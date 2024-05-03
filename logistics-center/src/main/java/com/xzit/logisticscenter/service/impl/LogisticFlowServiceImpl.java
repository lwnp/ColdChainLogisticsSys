package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzit.api.hardware.feign.WarehouseFeignClient;
import com.xzit.common.hardware.model.dto.WarehouseDataDTO;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.entity.WarehouseLog;
import com.xzit.common.logistics.model.dto.LogisticFlowDTO;
import com.xzit.common.logistics.model.dto.SimpleLogisticFlowDTO;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.hardware.entity.IOTData;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.logisticscenter.mapper.ArrangementMapper;
import com.xzit.logisticscenter.mapper.LogisticFlowMapper;
import com.xzit.logisticscenter.mapper.WarehouseLogMapper;
import com.xzit.logisticscenter.service.LogisticFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogisticFlowServiceImpl implements LogisticFlowService {
    private final LogisticFlowMapper logisticFlowMapper;
    private final WarehouseLogMapper warehouseLogMapper;
    private final WarehouseFeignClient warehouseFeignClient;
    private final ArrangementMapper arrangementMapper;
    @Value("${live.stream}")
    private String baseUrl;
    @Override
    public List<SimpleLogisticFlowDTO> getSimpleLogisticFlowByOrderNum(String orderNum) {
        return logisticFlowMapper.getSimpleLogisticFlowByOrderNum(orderNum);
    }

    @Override
    public List<LogisticFlowDTO> getLogisticFlowByOrderNum(String orderNum) {
        return logisticFlowMapper.getLogisticFlowByOrderNum(orderNum);
    }

    @Override
    public byte[] getLocationFile(String orderNum) {
        List<IOTData> iotDataList = logisticFlowMapper.getLocationDataByOrderNum(orderNum);
        if(iotDataList.isEmpty()){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        List<String> coordinates = iotDataList.stream()
                .map(data -> "[" + data.getLongitude() + "," + data.getLatitude() + "]")
                .collect(Collectors.toList());
        try {
            return mapper.writeValueAsBytes(coordinates);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<WarehouseDataDTO> getWarehouseData(String orderNum) {
        List<WarehouseLog> warehouseLogList = warehouseLogMapper.selectList(new QueryWrapper<WarehouseLog>().eq("order_num", orderNum));
        if(warehouseLogList.isEmpty()){
            throw new BizException("暂无数据");
        }
        List<WarehouseDataDTO> warehouseDataDTOList=warehouseFeignClient.getWarehouseData(orderNum).getData();
        if(warehouseDataDTOList.isEmpty()){
            throw new BizException("暂无数据");
        }
        return warehouseDataDTOList;
    }

    @Override
    public String getWarehouseLiveStreamUrl(String orderNum) {
        WarehouseLog warehouseLog = warehouseLogMapper.selectOne(new QueryWrapper<WarehouseLog>().eq("order_num", orderNum).eq("is_stored", 1));
        if(warehouseLog==null){
            throw new BizException("货物不在库中无法查看");
        }
        return baseUrl+"/center"+ warehouseLog.getCenterId();
    }

    @Override
    public List<String> getCarLiveStreamUrl(String orderNum) {
        List<Arrangement> arrangementList=arrangementMapper.selectList(new QueryWrapper<Arrangement>().eq("order_num", orderNum).eq("status_id", 2));
        if(arrangementList.isEmpty()){
            throw new BizException("货物不在运输中无法查看");
        }
        List<String> keys = new ArrayList<>();
        for (Arrangement arrangement : arrangementList) {
            keys.add(baseUrl+"/car"+ arrangement.getCarId());
        }
        return keys;
    }

    @Override
    public Long getCenterIdByOrderNum(String orderNum) {
        WarehouseLog warehouseLog = warehouseLogMapper.selectOne(new QueryWrapper<WarehouseLog>().eq("order_num", orderNum).eq("is_stored", 1));
        return warehouseLog.getCenterId();
    }


}
