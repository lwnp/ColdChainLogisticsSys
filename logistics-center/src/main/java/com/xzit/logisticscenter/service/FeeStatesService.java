package com.xzit.logisticscenter.service;

import com.xzit.common.logistics.model.dto.FeeStatesDTO;
import com.xzit.common.logistics.model.vo.FeeStatesVO;

import java.math.BigDecimal;
import java.util.List;

public interface FeeStatesService {
    List<FeeStatesDTO> getFeeStates();
    void modifyFeeStates(FeeStatesVO feeStatesVO);
    void addFeeStates(FeeStatesVO feeStatesVO);

}
