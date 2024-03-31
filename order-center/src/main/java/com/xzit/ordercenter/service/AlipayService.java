package com.xzit.ordercenter.service;

import com.alipay.api.AlipayApiException;
import com.xzit.common.order.entity.Order;

public interface AlipayService {
    String generatePaymentUrl(Order order) throws AlipayApiException;

}
