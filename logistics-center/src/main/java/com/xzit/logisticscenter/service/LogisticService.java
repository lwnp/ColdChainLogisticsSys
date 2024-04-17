package com.xzit.logisticscenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.model.dto.*;
import com.xzit.common.logistics.model.vo.LogisticFlowVO;
import com.xzit.common.sys.model.vo.QueryVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface LogisticService {
    Map<String,Double> address2Location(String address);
    ArrangeDistanceDTO  getArrangeDistance(Long from,Long to,Long goods);
    BigDecimal arrangeOrder(String orderNum);
    void recoverArrange(String orderNum);
    void startShipping(String orderNum);
    IPage<ArrangementDTO> getArrangementByQuery(QueryVO queryVO);
    List<ArrangementDTO> getCourierArrangement();
    AddressInfoDTO courierGetUserAddress(String orderNum);
    void pickUpConfirm(LogisticFlowVO logisticFlowVO);
    void senderStationArriveConfirm(LogisticFlowVO logisticFlowVO);
    void senderStationReleaseConfirm(LogisticFlowVO logisticFlowVO);
    void senderCenterArriveConfirmAndStored(LogisticFlowVO logisticFlowVO);
    IPage<ArrangementDTO> getHistoryArrangementByQuery(QueryVO queryVO);
    void senderCenterDropAndReleaseConfirm(LogisticFlowVO logisticFlowVO);
    void receiveCenterArriveConfirmAndStored(LogisticFlowVO logisticFlowVO);
    void receiveCenterDropAndReleaseConfirm(LogisticFlowVO logisticFlowVO);
    void receiveStationArriveConfirm(LogisticFlowVO logisticFlowVO);
    void receiveConfirm(LogisticFlowVO logisticFlowVO);
}
