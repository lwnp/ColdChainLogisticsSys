package com.xzit.logisticscenter.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.api.order.feign.GoodsFeignClient;
import com.xzit.api.order.feign.OrderFeignClient;
import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.logistics.constant.LogisticConstant;
import com.xzit.common.logistics.entity.*;
import com.xzit.common.logistics.model.dto.*;
import com.xzit.common.logistics.model.vo.AddressInfoVO;
import com.xzit.common.logistics.model.vo.LocationResultVO;
import com.xzit.common.logistics.model.vo.LocationVO;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.model.vo.GoodsVO;
import com.xzit.common.order.utils.FeeCalculator;
import com.xzit.common.sys.constant.MQConstant;
import com.xzit.common.sys.entity.Notice;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.logisticscenter.mapper.*;
import com.xzit.logisticscenter.service.FeeStatesService;
import com.xzit.logisticscenter.service.LogisticService;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogisticServiceImpl implements LogisticService {
    private final WebClient webClient;
    private final LogisticMapper logisticMapper;
    private final ArrangementMapper arrangementMapper;
    private final CourierMapper courierMapper;
    private final AddressInfoMapper addressInfoMapper;
    private final GoodsFeignClient goodsFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final FeeStatesMapper feeStatesMapper;
    private final CenterMapper centerMapper;
    private final CarMapper carMapper;
    private final UserFeignClient userFeignClient;
    private final StreamBridge streamBridge;

    @Override
    public Map<String, Double> address2Location(String address) {
        Mono<ResponseEntity<LocationResultVO>> result=webClient.get()
                .uri(LogisticConstant.ADDRESS_TO_LOCATION_API,address,LogisticConstant.APP_KEY)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(LocationResultVO.class);

        ResponseEntity<LocationResultVO> responseEntity = result.block();
        if(responseEntity==null||responseEntity.getBody()==null) {
            throw new BizException("地址转换失败 腾讯API异常");
        }
        LocationVO locationVO=responseEntity.getBody().getResult().getLocation();
        Map<String,Double> location=new HashMap<>();
        location.put("latitude",locationVO.getLat());
        location.put("longitude",locationVO.getLng());
        return location;
    }

    @Override
    public ArrangeDistanceDTO getArrangeDistance(Long from, Long to, Long goods) {
        AddressInfo fromAddress=addressInfoMapper.selectById(from);
        AddressInfo toAddress=addressInfoMapper.selectById(to);
        Goods userGoods=goodsFeignClient.getGoodsById(goods).getData();
        List<Arrangement> arrangements = getArrangement(fromAddress, toAddress, userGoods);
       if (arrangements==null){
           return null;
       }
       Map<String,Double> fromLocation=address2Location(fromAddress.getAddress());
       Map<String,Double> toLocation=address2Location(toAddress.getAddress());
       Double distance=getDistance(fromLocation,toLocation);
       ArrangeDistanceDTO  arrangeDistanceDTO=new ArrangeDistanceDTO();
        arrangeDistanceDTO.setDistance(distance*2);
        arrangeDistanceDTO.setArrangementList(arrangements);
        return arrangeDistanceDTO;
    }

    @Override
    @Transactional
    public BigDecimal arrangeOrder(String orderNum) {
        Order order=orderFeignClient.getOrderByOrderNum(orderNum).getData();
        AddressInfo fromAddress=addressInfoMapper.selectById(order.getSenderAddressId());
        if(fromAddress==null|| !Objects.equals(fromAddress.getUserInfoId(), order.getUserInfoId())){
            throw new BizException("发货地址不存在或信息不匹配");
        }
        AddressInfo toAddress=addressInfoMapper.selectById(order.getReceiveAddressId());
        if(toAddress==null|| !Objects.equals(toAddress.getUserInfoId(), order.getUserInfoId())){
            throw new BizException("收货地址不存在或信息不匹配");
        }
        ArrangeDistanceDTO arrangeDistanceDTO=getArrangeDistance(fromAddress.getId(),toAddress.getId(),order.getGoodsId());
        if (arrangeDistanceDTO==null){
            throw new BizException("没有合适的配送方案");
        }
        List<Arrangement> arrangementList=arrangeDistanceDTO.getArrangementList();
        arrangementList.forEach(arrangement -> {
            arrangement.setOrderNum(orderNum);
            arrangementMapper.insert(arrangement);
        });
        lockArrange(arrangementList);
        List<FeeStates> feeStatesList=feeStatesMapper.getAllFeeList();
        Goods goods=goodsFeignClient.getGoodsById(order.getGoodsId()).getData();
        return FeeCalculator.calculateShippingCost(arrangeDistanceDTO.getDistance().intValue()+1,goods.getWeight().intValue()+1,goods.getSpace().intValue()+1,feeStatesList);
    }

    @Override
    public void recoverArrange(String orderNum) {
        List<Arrangement> arrangementList=arrangementMapper.selectList(new QueryWrapper<Arrangement>().eq("order_num",orderNum));
        arrangementList.forEach(arrangement -> {
            if(arrangement.getStepId()==3L||arrangement.getStepId()==4L){
                Goods goods=goodsFeignClient.getGoodsByOrderNum(arrangement.getOrderNum()).getData();
                Double space=goods.getSpace();
                Center center=centerMapper.selectById(arrangement.getToId());
                center.setFreeSpace(center.getFreeSpace()+space);
                centerMapper.updateById(center);
            }
            Courier courier=courierMapper.selectById(arrangement.getCourierId());
            courier.setIsInUse(false);
            courierMapper.updateById(courier);
            Car car=carMapper.selectById(arrangement.getCarId());
            car.setIsInUse(false);
            carMapper.updateById(car);
        });
        arrangementMapper.deleteBatchIds(arrangementList.stream().map(Arrangement::getId).collect(Collectors.toList()));
    }

    @Override
    public void startShipping(String orderNum) {
        List<Arrangement> arrangementList=arrangementMapper.selectList(new QueryWrapper<Arrangement>().eq("order_num",orderNum).and(q->q.eq("step_id",1).and(f->f.eq("status_id",1))));
        if(arrangementList.isEmpty()){
            throw new BizException("非法订单号");
        }
        arrangementList.forEach(arrangement -> {
            arrangement.setStatusId(2L);
            arrangementMapper.updateById(arrangement);
            Courier courier=courierMapper.selectById(arrangement.getCourierId());
            Notice notice= Notice.builder()
                    .userInfoId(courier.getUserInfoId())
                    .title(String.format(MQConstant.COURIER_ARRANGE_TITLE, arrangement.getOrderNum()))
                    .isAdminMessage(false)
                    .content(MQConstant.COURIER_ARRANGE_CONTENT).build();
            streamBridge.send(MQConstant.NOTICE_EXCHANGE, MessageBuilder.withPayload(notice).build());
        });

    }

    @Override
    public IPage<ArrangementDTO> getArrangementByQuery(QueryVO queryVO) {
        Page<ArrangementDTO> page=new Page<>(queryVO.getPageNum(),queryVO.getPageSize());
        return arrangementMapper.getArrangementByQuery(page,queryVO);
    }

    @Override
    public ArrangementDTO getCourierArrangement() {
        UserInfoDTO userInfoDTO=getUserInfo();
        return arrangementMapper.getArrangementByUserInfoId(userInfoDTO.getId());
    }


    private List<AvailableLogisticDTO> getAvailableStation(Long areaId) {
        return logisticMapper.getAvailableStation(areaId);

    }
    private List<AvailableLogisticDTO> getAvailableCenter(Long areaId,Double goodsSpace){
        return logisticMapper.getAvailableCenter(areaId,goodsSpace);
    }
    public Double getDistance(Map<String,Double> fromLocation,Map<String,Double> toLocation){
        double earthRadius = 6371;

        double fromLat = Math.toRadians(fromLocation.get("latitude"));
        double fromLng = Math.toRadians(fromLocation.get("longitude"));
        double toLat = Math.toRadians(toLocation.get("latitude"));
        double toLng = Math.toRadians(toLocation.get("longitude"));

        double dLat = toLat - fromLat;
        double dLng = toLng - fromLng;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(fromLat) * Math.cos(toLat)
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;

    }
    private List<Arrangement> selectVehicles(List<CarDTO> carList, List<CourierDTO> courierList, double goodsWeight, double goodsSpaces, Long stepId,List<Arrangement> before) {
        List<Arrangement> arrangements = new ArrayList<>();
        double remainingWeight = goodsWeight;
        double remainingSpace = goodsSpaces;
        Set<Long> selectedCourierIds = new HashSet<>();

        // 如果车辆或司机列表为空，则直接返回null
        if (carList.isEmpty() || courierList.isEmpty()) {
            return null;
        }
        double totalLoad=0;
        double totalSpace=0;
        for (CarDTO car : carList) {
            totalLoad+=car.getMaxLoad();
            totalSpace+=car.getMaxSpace();
        }

        // 遍历车辆列表，找到合适的车辆
        for (CarDTO car : carList) {
            if (remainingWeight <= 0 && remainingSpace <= 0) {
                break;
            }

            // 如果当前车辆未被使用并且能够容纳货物
            if (totalLoad >= remainingWeight && totalSpace >= remainingSpace) {
                // 随机选择一个司机
                CourierDTO courier = getRandomCourier(courierList,selectedCourierIds);
                // 如果没有可用的司机，则返回null
                if (courier == null) {
                    return null;
                }
                if (before!=null){
                    for(Arrangement beforeArrange:before){
                        Courier courier1 = courierMapper.selectById(courier.getId());
                        beforeArrange.setToId(courier1.getLogisticId());
                    }
                }

                // 创建安排并添加到列表中
                arrangements.add(Arrangement.builder()
                        .carId(car.getId())
                        .courierId(courier.getId())
                        .stepId(stepId)
                        .build());
                // 更新剩余的重量和空间
                remainingWeight -= car.getMaxLoad();
                remainingSpace -= car.getMaxSpace();
            }
        }

        // 如果仍有未满足的货物，则返回null
        if (remainingWeight > 0 || remainingSpace > 0) {
            return null;
        }

        return arrangements;
    }

    private CourierDTO getRandomCourier(List<CourierDTO> courierList, Set<Long> selectedCourierIds) {
        if (courierList.isEmpty()) {
            return null;
        }

        // 过滤掉已经被选择过的司机
        List<CourierDTO> availableCouriers = courierList.stream()
                .filter(courier -> !selectedCourierIds.contains(courier.getId()))
                .toList();

        // 如果所有司机都已经被选择过，则返回null
        if (availableCouriers.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(availableCouriers.size());
        return availableCouriers.get(index);
    }
    private List<ResultPairDTO> getSortedResultPairDTOList(Map<String, Double> location, List<AvailableLogisticDTO> stations, List<AvailableLogisticDTO> centers) {
        List<ResultPairDTO> resultPairDTOList = new ArrayList<>();
        for (AvailableLogisticDTO fromStation : stations) {
            Map<String, Double> fromStationLocation = new HashMap<>();
            fromStationLocation.put("longitude", fromStation.getLongitude());
            fromStationLocation.put("latitude", fromStation.getLatitude());
            Double station2User = getDistance(location, fromStationLocation);
            for (AvailableLogisticDTO fromCenter : centers) {
                Map<String, Double> fromCenterLocation = new HashMap<>();
                fromCenterLocation.put("longitude", fromCenter.getLongitude());
                fromCenterLocation.put("latitude", fromCenter.getLatitude());
                Double station2Center = getDistance(fromStationLocation, fromCenterLocation);
                resultPairDTOList.add(new ResultPairDTO(fromStation, fromCenter, station2Center + station2User));
            }
        }
        resultPairDTOList.sort(Comparator.comparingDouble(ResultPairDTO::getDistance));
        return resultPairDTOList;
    }
    private UserInfoDTO getUserInfo(){
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        return userFeignClient.getUserInfo(userId).getData();
    }
    public List<Arrangement> getArrangement(AddressInfo from, AddressInfo to, Goods goods) {
        Long fromAreaId = from.getAreaId();
        Long toAreaId = to.getAreaId();
        String fromAddress = from.getAddress();
        String toAddress = to.getAddress();
        Double goodsWeight = goods.getWeight();
        Double goodsSpace = goods.getSpace();
        Map<String, Double> fromLocation = address2Location(fromAddress);
        Map<String, Double> toLocation = address2Location(toAddress);
        List<AvailableLogisticDTO> fromStations = getAvailableStation(fromAreaId);
        List<AvailableLogisticDTO> fromCenters = getAvailableCenter(fromAreaId, goodsSpace);
        List<AvailableLogisticDTO> toStations = getAvailableStation(toAreaId);
        List<AvailableLogisticDTO> toCenters = getAvailableCenter(toAreaId, goodsSpace);
        List<Arrangement> result = new ArrayList<>();
        List<Arrangement> temp=new ArrayList<>();
        List<Arrangement> toArrangement=new ArrayList<>();
        AvailableLogisticDTO center=null,station=null;
        if (fromStations.isEmpty() || fromCenters.isEmpty()) {
            return null;
        }
        List<ResultPairDTO> fromResultPairDTOList = getSortedResultPairDTOList(fromLocation, fromStations, fromCenters);
        List<ResultPairDTO> toResultPairDTOList = getSortedResultPairDTOList(toLocation, toStations, toCenters);
        for (ResultPairDTO fromResultPairDTO : fromResultPairDTOList) {
            List<Arrangement> station2Center=new ArrayList<>();
            List<Arrangement> user2Station=selectVehicles(fromResultPairDTO.getStation().getCarList(),fromResultPairDTO.getStation().getCourierList(),goodsWeight,goodsSpace,1L,null);
            if(user2Station==null){
                continue;
            }
            result.addAll(user2Station);
            for(Arrangement arrangement:user2Station){
                Arrangement arr=SerializationUtils.clone(arrangement);
                arr.setStepId(3L);
                station2Center.add(arr);
            }
            List<Arrangement> center2Center=selectVehicles(fromResultPairDTO.getCenter().getCarList(),fromResultPairDTO.getCenter().getCourierList(),goodsWeight,goodsSpace,4L,station2Center);
            if(center2Center==null){
                continue;
            }
            for(Arrangement arrangement:user2Station){
                    Arrangement arr=SerializationUtils.clone(arrangement);
                    arr.setStepId(2L);
                    arr.setToId(fromResultPairDTO.getStation().getId());
                    result.add(arr);
            }
            result.addAll(station2Center);
            temp.addAll(center2Center);
            center=fromResultPairDTO.getCenter();
            station=fromResultPairDTO.getStation();
            break;
        }
            if(result.isEmpty()){
            return null;
        }else {
                for(ResultPairDTO toResultPairDTO:toResultPairDTOList){
                   if(toResultPairDTO.getStation().equals(station)){
                       continue;
                   }
                    List<Arrangement> center2Station=selectVehicles(toResultPairDTO.getCenter().getCarList(),toResultPairDTO.getCenter().getCourierList(),goodsWeight,goodsSpace,5L,temp);
                    if(center2Station==null){
                        continue;
                    }
                    result.addAll(temp);
                    List<Arrangement> station2User=selectVehicles(toResultPairDTO.getStation().getCarList(),toResultPairDTO.getStation().getCourierList(),goodsWeight,goodsSpace,6L,center2Station);
                    if(station2User==null){
                        continue;
                    }
                    toArrangement.addAll(center2Station);
                    toArrangement.addAll(station2User);
                    if(toResultPairDTO.getCenter().equals(center)){
                        result = result.stream()
                                .filter(arrangement -> arrangement.getStepId() != 4L)
                                .collect(Collectors.toList());
                        break;
                    }
                    break;

                }


        }
        if(toArrangement.isEmpty()){
            return null;
        }
        result.addAll(toArrangement);

        return result;
    }
    public void lockArrange(List<Arrangement> arrangementList){
        arrangementList.forEach(arrangement -> {
            if(arrangement.getStepId()==3L||arrangement.getStepId()==4L){
                Goods goods=goodsFeignClient.getGoodsByOrderNum(arrangement.getOrderNum()).getData();
                Double space=goods.getSpace();
                Center center=centerMapper.selectById(arrangement.getToId());
                center.setFreeSpace(center.getFreeSpace()-space);
                centerMapper.updateById(center);
            }
            Car car=carMapper.selectById(arrangement.getCarId());
            car.setIsInUse(true);
            carMapper.updateById(car);
            Courier courier=courierMapper.selectById(arrangement.getCourierId());
            courier.setIsInUse(true);
            courierMapper.updateById(courier);
        });
    }


}

