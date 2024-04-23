package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.logistics.entity.WarehouseLog;
import com.xzit.common.logistics.model.dto.LogisticFlowDTO;
import com.xzit.common.logistics.model.dto.SimpleLogisticFlowDTO;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.commonhardware.entity.IOTData;
import com.xzit.commonhardware.entity.WarehouseData;
import com.xzit.logisticscenter.mapper.LogisticFlowMapper;
import com.xzit.logisticscenter.mapper.WarehouseLogMapper;
import com.xzit.logisticscenter.service.LogisticFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogisticFlowServiceImpl implements LogisticFlowService {
    private final LogisticFlowMapper logisticFlowMapper;
    private final WarehouseLogMapper warehouseLogMapper;
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
    public List<WarehouseData> getWarehouseData(String orderNum) {
        List<WarehouseLog> warehouseLogList = warehouseLogMapper.selectList(new QueryWrapper<WarehouseLog>().eq("order_num", orderNum));
        if(warehouseLogList.isEmpty()){
            throw new BizException("暂无数据");
        }
        return List.of();
    }


}
