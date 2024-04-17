package com.xzit.logisticscenter.service;

import com.xzit.common.logistics.entity.LogisticFlow;
import com.xzit.common.logistics.model.dto.LogisticFlowDTO;
import com.xzit.common.logistics.model.dto.SimpleLogisticFlowDTO;

import java.util.List;

public interface LogisticFlowService {
    List<SimpleLogisticFlowDTO> getSimpleLogisticFlowByOrderNum(String orderNum);
    List<LogisticFlowDTO> getLogisticFlowByOrderNum(String orderNum);
}
