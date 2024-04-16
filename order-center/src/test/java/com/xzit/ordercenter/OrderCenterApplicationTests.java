package com.xzit.ordercenter;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.xzit.common.order.entity.Order;
import com.xzit.ordercenter.service.AlipayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest
class OrderCenterApplicationTests {
    @Autowired
    AlipayService a;
    @Autowired
    AlipayTradeQueryRequest queryRequest;
    @Autowired
    AlipayClient alipayClient;

    @Test
    void contextLoads() throws AlipayApiException {

    }

}
