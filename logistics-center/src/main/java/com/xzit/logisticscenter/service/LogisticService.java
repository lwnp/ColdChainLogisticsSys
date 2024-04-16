package com.xzit.logisticscenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.model.dto.*;
import com.xzit.common.logistics.model.vo.AddressInfoVO;
import com.xzit.common.logistics.model.vo.LogisticFlowVO;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.model.vo.GoodsVO;
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
    ArrangementDTO getCourierArrangement();
    AddressInfoDTO courierGetUserAddress(String orderNum);
    void pickUpConfirm(String orderNum, LogisticFlowVO logisticFlowVO);
}
