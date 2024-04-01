package com.xzit.ordercenter;

import com.alipay.api.AlipayApiException;
import com.xzit.common.order.entity.Order;
import com.xzit.ordercenter.service.AlipayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class OrderCenterApplicationTests {
    @Autowired
    AlipayService a;

    @Test
    void contextLoads() throws AlipayApiException {
        Order order=Order.builder()
                        .id(1L)
                        .orderNum(UUID.randomUUID().toString().replace("-",""))
                                .price(new BigDecimal("66.02"))
                                        .build();
        System.out.println(order);
        System.out.println(a.generatePaymentUrl(order));
    }

}
