package com.xzit.logisticscenter.service;

import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.model.dto.AvailableLogisticDTO;
import com.xzit.common.logistics.model.dto.FeeStatesDTO;
import com.xzit.common.logistics.model.vo.AddressInfoVO;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.model.vo.GoodsVO;

import java.util.List;
import java.util.Map;

public interface LogisticService {
    Map<String,Double> address2Location(String address);
    Boolean arrangeLogistic(AddressInfoVO from, AddressInfoVO to, GoodsVO goodsVO, Long orderId);

}
