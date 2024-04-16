package com.xzit.ordercenter.service;


import com.alipay.api.AlipayApiException;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.model.vo.OrderVO;

import java.util.function.Function;

public interface OrderService {
    Order getOrderByOrderNum(String orderNum);
    String generateAlipayUrl(OrderVO orderVO);
    void updatePayStatus(String orderNum);
}
