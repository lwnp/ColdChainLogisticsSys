package com.xzit.logisticscenter.service.impl;

import com.alibaba.fastjson2.JSON;
import com.xzit.common.logistics.constant.LogisticConstant;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.entity.Courier;
import com.xzit.common.logistics.model.dto.AvailableLogisticDTO;
import com.xzit.common.logistics.model.dto.CarDTO;
import com.xzit.common.logistics.model.dto.CourierDTO;
import com.xzit.common.logistics.model.dto.ResultPairDTO;
import com.xzit.common.logistics.model.vo.AddressInfoVO;
import com.xzit.common.logistics.model.vo.LocationResultVO;
import com.xzit.common.logistics.model.vo.LocationVO;
import com.xzit.common.order.model.vo.GoodsVO;
import com.xzit.common.sys.exception.BizException;
import com.xzit.logisticscenter.mapper.ArrangementMapper;
import com.xzit.logisticscenter.mapper.CourierMapper;
import com.xzit.logisticscenter.mapper.LogisticMapper;
import com.xzit.logisticscenter.service.LogisticService;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogisticServiceImpl implements LogisticService {
    private final WebClient webClient;
    private final LogisticMapper logisticMapper;
    private final ArrangementMapper arrangementMapper;
    private final CourierMapper courierMapper;

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
    public Boolean arrangeLogistic(AddressInfoVO from, AddressInfoVO to, GoodsVO goodsVO, Long orderId) {
        List<Arrangement> arrangementList = getArrangement(from.getAreaId(), to.getAreaId(), from.getAddress(), to.getAddress(), goodsVO.getWeight(), goodsVO.getSpace());
        if (arrangementList != null) {
            arrangementList.forEach(arrangement -> arrangement.setOrderId(orderId));
            arrangementMapper.batchInsert(arrangementList);
            return true;
        }
        return false;
    }

    @Override
    public List<Arrangement> test(Long fromAreaId, Long toAreaId, String fromAddress, String toAddress, Double goodsWeight, Double goodsSpace) {
        return getArrangement(fromAreaId, toAreaId, fromAddress, toAddress, goodsWeight, goodsSpace);
    }

    @Override
    public List<AvailableLogisticDTO> getAvailableLogistic(Long areaId) {
        return getAvailableStation(areaId);
    }

    @Override
    public List<AvailableLogisticDTO> getAvailableLogistic(Long areaId, Double space) {
        return getAvailableCenter(areaId, space);
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

        // 遍历车辆列表，找到合适的车辆
        for (CarDTO car : carList) {
            if (remainingWeight <= 0 && remainingSpace <= 0) {
                break;
            }

            // 如果当前车辆未被使用并且能够容纳货物
            if (car.getMaxLoad() >= remainingWeight && car.getMaxSpace() >= remainingSpace) {
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

    private List<Arrangement> getArrangement(Long fromAreaId, Long toAreaId, String fromAddress, String toAddress, Double goodsWeight, Double goodsSpace) {
        Map<String, Double> fromLocation = address2Location(fromAddress);
        Map<String, Double> toLocation = address2Location(toAddress);
        List<AvailableLogisticDTO> fromStations = getAvailableStation(fromAreaId);
        List<AvailableLogisticDTO> fromCenters = getAvailableCenter(fromAreaId, goodsSpace);
        List<Arrangement> result = new ArrayList<>();

        if (fromStations.isEmpty() || fromCenters.isEmpty()) {
            return null;
        }

        boolean sameArea = Objects.equals(fromAreaId, toAreaId);
        if (sameArea) {
            while (!fromCenters.isEmpty()){
                List<AvailableLogisticDTO> tempRemovedCenters = new ArrayList<>();
                result = handleSameAreaLogic(fromLocation, toLocation, fromStations, fromCenters, goodsWeight, goodsSpace, tempRemovedCenters);
                if (result == null) {
                    fromCenters.removeAll(tempRemovedCenters);
                }
                else break;
            }
        } else {
            List<Arrangement> tempArr=new ArrayList<>();
            List<AvailableLogisticDTO> toCenters = getAvailableCenter(toAreaId, goodsSpace);
            List<AvailableLogisticDTO> toStations = getAvailableStation(toAreaId);
            List<ResultPairDTO> toStationCenterPairList = getSortedResultPairDTOList(toLocation, toStations, toCenters);
            List<ResultPairDTO> fromStationCenterPairList = getSortedResultPairDTOList(fromLocation, fromStations, fromCenters);
            for(ResultPairDTO resultPairDTO:fromStationCenterPairList){
                List<Arrangement> station2User=selectVehicles(resultPairDTO.getStation().getCarList(),resultPairDTO.getStation().getCourierList(),goodsWeight,goodsSpace,1L,null);
                if (station2User==null){
                    continue;
                }
                result.addAll(station2User);
                for(Arrangement arrangement:station2User){
                    Arrangement copy= SerializationUtils.clone(arrangement);
                    copy.setStepId(2L);
                    copy.setToId(resultPairDTO.getStation().getId());
                    result.add(copy);
                }
                for(Arrangement arrangement:station2User){
                    Arrangement copy= SerializationUtils.clone(arrangement);
                    copy.setStepId(3L);
                    copy.setToId(resultPairDTO.getCenter().getId());
                    tempArr.add(copy);
                }
                List<Arrangement> center2center=selectVehicles(resultPairDTO.getCenter().getCarList(),resultPairDTO.getCenter().getCourierList(),goodsWeight,goodsSpace,4L,tempArr);
                if(center2center==null){
                    continue;
                }
                result.addAll(tempArr);
                tempArr.clear();
                tempArr.addAll(center2center);
            }
            if(result.isEmpty()){
                return null;
            }
            else {
                for(ResultPairDTO resultPairDTO:toStationCenterPairList){
                    List<Arrangement> center2station=selectVehicles(resultPairDTO.getCenter().getCarList(),resultPairDTO.getCenter().getCourierList(),goodsWeight,goodsSpace,5L,tempArr);
                    if(center2station==null){
                        continue;
                    }
                    result.addAll(tempArr);
                   List<Arrangement> station2user=selectVehicles(resultPairDTO.getStation().getCarList(),resultPairDTO.getStation().getCourierList(),goodsWeight,goodsSpace,6L,center2station);
                   if(station2user==null){
                        continue;
                   }
                    result.addAll(center2station);
                    result.addAll(station2user);
                    break;
                }
            }


        }

        if (result == null || result.isEmpty()) {
            return null;
        }
        return result;
    }

    private List<Arrangement> handleSameAreaLogic(Map<String, Double> fromLocation, Map<String, Double> toLocation, List<AvailableLogisticDTO> fromStations, List<AvailableLogisticDTO> fromCenters, Double goodsWeight, Double goodsSpace, List<AvailableLogisticDTO> removedCenters) {
        List<Arrangement> result = new ArrayList<>();
        AvailableLogisticDTO temp = null;
        List<Arrangement> tempArr = new ArrayList<>();
        List<ResultPairDTO> station2Center = getSortedResultPairDTOList(fromLocation, fromStations, fromCenters);

        for (ResultPairDTO resultPairDTO : station2Center) {
            AvailableLogisticDTO center = resultPairDTO.getCenter();
            AvailableLogisticDTO station = resultPairDTO.getStation();
            List<Arrangement> fromStationArrange = selectVehicles(station.getCarList(), station.getCourierList(), goodsWeight, goodsSpace, 1L, null);
            if (fromStationArrange == null) {
                continue;
            }
            result.addAll(fromStationArrange);
            for (Arrangement arrangement : fromStationArrange) {
                Arrangement copy = SerializationUtils.clone(arrangement);
                copy.setStepId(2L);
                copy.setToId(station.getId());
                result.add(copy);
            }
            for (Arrangement arrangement : fromStationArrange) {
                Arrangement copy = SerializationUtils.clone(arrangement);
                copy.setStepId(3L);
                copy.setToId(center.getId());
                tempArr.add(copy);
            }
            temp = center;
            break;
        }

        if (result.isEmpty()) {
            return null;
        }

        List<ResultPairDTO> center2Station = getSortedResultPairDTOList(toLocation, fromStations, fromCenters);
        for (ResultPairDTO resultPairDTO : center2Station) {
            if (!resultPairDTO.getCenter().equals(temp)) {
                continue;
            }
            List<Arrangement> center2StationArrange = selectVehicles(resultPairDTO.getCenter().getCarList(), resultPairDTO.getCenter().getCourierList(), goodsWeight, goodsSpace, 5L, tempArr);
            if (center2StationArrange == null) {
                removedCenters.add(resultPairDTO.getCenter());
                continue;
            }
            result.addAll(tempArr);
            List<Arrangement> station2User = selectVehicles(resultPairDTO.getStation().getCarList(), resultPairDTO.getStation().getCourierList(), goodsWeight, goodsSpace, 6L, center2StationArrange);
            if (station2User == null) {
                removedCenters.add(resultPairDTO.getCenter());
                continue;
            }
            result.addAll(center2StationArrange);
            result.addAll(station2User);
            break;
        }

        return result;
    }
}

