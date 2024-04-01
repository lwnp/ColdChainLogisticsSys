package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzit.common.logistics.constant.LogisticConstant;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.entity.Center;
import com.xzit.common.logistics.entity.Station;
import com.xzit.common.logistics.model.dto.AvailableLogisticDTO;
import com.xzit.common.logistics.model.dto.CarDTO;
import com.xzit.common.logistics.model.dto.CourierDTO;
import com.xzit.common.logistics.model.dto.ResultPairDTO;
import com.xzit.common.logistics.model.vo.LocationResultVO;
import com.xzit.common.logistics.model.vo.LocationVO;
import com.xzit.common.sys.exception.BizException;
import com.xzit.logisticscenter.mapper.CenterMapper;
import com.xzit.logisticscenter.mapper.LogisticMapper;
import com.xzit.logisticscenter.mapper.StationMapper;
import com.xzit.logisticscenter.service.LogisticService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LogisticServiceImpl implements LogisticService {
    private final WebClient webClient;
    private final LogisticMapper logisticMapper;

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
    private List<AvailableLogisticDTO> getAvailableStation(Long areaId) {
        return logisticMapper.getAvailableStation(areaId);

    }
    private List<AvailableLogisticDTO> getAvailableCenter(Long areaId,Double goodsSpace){
        return logisticMapper.getAvailableCenter(areaId,goodsSpace);
    }
    private Double getDistance(Map<String,Double> fromLocation,Map<String,Double> toLocation){
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
    private List<Arrangement> selectVehicles(List<CarDTO> carList, List<CourierDTO> courierList, double goodsWeight, double goodsSpaces,Long stepId) {
        int n = carList.size();
        int m = courierList.size();
        double[][][] dp = new double[n + 1][m + 1][(int) (goodsWeight + 1)];
        int[][][] pre = new int[n + 1][m + 1][(int) (goodsWeight + 1)];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= goodsWeight; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        dp[0][0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                for (int k = 0; k <= goodsWeight; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    pre[i][j][k] = 0;
                    if (k >= carList.get(i - 1).getMaxLoad() && dp[i - 1][j - 1][(int) (k - carList.get(i - 1).getMaxLoad())] != -1 && dp[i - 1][j - 1][(int) (k - carList.get(i - 1).getMaxLoad())] + carList.get(i - 1).getMaxSpace() > dp[i][j][k]) {
                        dp[i][j][k] = dp[i - 1][j - 1][(int) (k - carList.get(i - 1).getMaxLoad())] + carList.get(i - 1).getMaxSpace();
                        pre[i][j][k] = 1;
                    }
                }
            }
        }

        int bestJ = 0, bestK = 0;
        for (int j = 1; j <= m; j++) {
            for (int k = 0; k <= goodsWeight; k++) {
                if (dp[n][j][k] != -1 && dp[n][j][k] >= goodsSpaces) {
                    bestJ = j;
                    bestK = k;
                    break;
                }
            }
        }

        if (bestJ == 0 || bestK == 0) {
            return null;
        }

        List<Arrangement> result = new ArrayList<>();
        int j = bestJ, k = bestK;
        for (int i = n; i > 0; i--) {
            if (pre[i][j][k] == 1) {
                result.add(Arrangement.builder()
                        .carId(carList.get(i - 1).getId())
                        .stepId(stepId)
                        .courierId(courierList.get(j - 1).getId())
                        .build());
                j--;
                k -= carList.get(i - 1).getMaxLoad();
            }
        }

        return result;
    }
    private List<ResultPairDTO> getSortedResultPairDTOList(Map<String, Double> location, List<AvailableLogisticDTO> stations, List<AvailableLogisticDTO> centers) {
        List<ResultPairDTO> resultPairDTOList = new ArrayList<>();
        for (AvailableLogisticDTO fromStation : stations) {
            Map<String, Double> fromStationLocation = new HashMap<>();
            fromStationLocation.put("longitude", fromStation.getLongitude());
            fromStationLocation.put("latitude", fromStation.getLatitude());
            for (AvailableLogisticDTO fromCenter : centers) {
                Map<String, Double> fromCenterLocation = new HashMap<>();
                fromCenterLocation.put("longitude", fromCenter.getLongitude());
                fromCenterLocation.put("latitude", fromCenter.getLatitude());
                Double station2User = getDistance(location, fromStationLocation);
                Double station2Center = getDistance(fromStationLocation, fromCenterLocation);
                resultPairDTOList.add(new ResultPairDTO(fromStation, fromCenter, station2Center + station2User * 2));
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
        if (fromStations.isEmpty() || fromCenters.isEmpty()) {
            return null;
        }
        if (!Objects.equals(fromAreaId, toAreaId)) {
            List<AvailableLogisticDTO> toStations = getAvailableStation(toAreaId);
            List<AvailableLogisticDTO> toCenters = getAvailableCenter(toAreaId, goodsSpace);
            if (toStations.isEmpty() || toCenters.isEmpty()) {
                return null;
            }
            List<ResultPairDTO> fromresultPairDTOList = getSortedResultPairDTOList(fromLocation, fromStations, fromCenters);
            List<Arrangement> result = new ArrayList<>();
            for (ResultPairDTO resultPairDTO : fromresultPairDTOList) {
                List<Arrangement> fromStationArrange = selectVehicles(resultPairDTO.getStation().getCarList(), resultPairDTO.getStation().getCourierList(), goodsWeight, goodsSpace, 1L);
                if (fromStationArrange == null) {
                    continue;
                }
                result.addAll(fromStationArrange);
                for (Arrangement arrangement : fromStationArrange) {
                    Arrangement copy = SerializationUtils.clone(arrangement);
                    copy.setStepId(2L);
                    result.add(copy);
                }
                for (Arrangement arrangement : fromStationArrange) {
                    Arrangement copy = SerializationUtils.clone(arrangement);
                    copy.setStepId(3L);
                    result.add(copy);
                }
                List<Arrangement> fromCenterArrange = selectVehicles(resultPairDTO.getCenter().getCarList(), resultPairDTO.getCenter().getCourierList(), goodsWeight, goodsSpace, 4L);
                if (fromCenterArrange == null) {
                    continue;
                }
                result.addAll(fromCenterArrange);
                break;
            }
            if (result.isEmpty()) {
                return null;
            } else {
                List<ResultPairDTO> toresultPairDTOList = getSortedResultPairDTOList(toLocation, toStations, toCenters);
                for (ResultPairDTO resultPairDTO : toresultPairDTOList){
                    List<Arrangement> center2Station = selectVehicles(resultPairDTO.getStation().getCarList(), resultPairDTO.getStation().getCourierList(), goodsWeight, goodsSpace, 5L);
                    if (center2Station == null) {
                        continue;
                    }
                    List<Arrangement> station2User = selectVehicles(resultPairDTO.getStation().getCarList(), resultPairDTO.getStation().getCourierList(), goodsWeight, goodsSpace, 6L);
                    if (station2User == null) {
                        continue;
                    }
                    result.addAll(center2Station);
                    result.addAll(station2User);
                    break;
                }
                return result;
            }
        }
        return null;
    }
}

