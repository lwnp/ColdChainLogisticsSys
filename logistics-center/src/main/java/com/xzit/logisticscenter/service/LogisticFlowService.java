package com.xzit.logisticscenter.service;

import com.xzit.common.hardware.model.dto.WarehouseDataDTO;
import com.xzit.common.logistics.model.dto.LogisticFlowDTO;
import com.xzit.common.logistics.model.dto.SimpleLogisticFlowDTO;

import java.util.List;

public interface LogisticFlowService {
    List<SimpleLogisticFlowDTO> getSimpleLogisticFlowByOrderNum(String orderNum);
    List<LogisticFlowDTO> getLogisticFlowByOrderNum(String orderNum);
    byte[] getLocationFile(String orderNum);
    List<WarehouseDataDTO> getWarehouseData(String orderNum);
    String getWarehouseLiveStreamUrl(String orderNum);
    List<String> getCarLiveStreamUrl(String orderNum);
    Long getCenterIdByOrderNum(String orderNum);


}
