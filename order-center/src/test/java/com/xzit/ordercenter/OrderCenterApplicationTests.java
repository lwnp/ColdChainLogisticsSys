package com.xzit.ordercenter;

import com.alipay.api.AlipayApiException;
import com.xzit.common.order.entity.FeeStates;
import com.xzit.common.order.entity.Order;
import com.xzit.ordercenter.service.AlipayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.xzit.common.order.utils.FeeCalculator.calculateShippingCost;

@SpringBootTest
class OrderCenterApplicationTests {
    @Autowired
    AlipayService a;

    @Test
    void contextLoads() throws AlipayApiException {
        List<FeeStates> feeStatesList = List.of(
                FeeStates.builder().id(1L).feeTypeId(1L).limit(0.0).price(BigDecimal.valueOf(100)).oilRate(0.2).state(1L).build(), // 起步价
                FeeStates.builder().id(2L).feeTypeId(2L).limit(50.0).price(BigDecimal.valueOf(1.0)).oilRate(0.2).state(1L).build(),
                FeeStates.builder().id(3L).feeTypeId(2L).limit(100.0).price(BigDecimal.valueOf(1.2)).oilRate(0.2).state(2L).build(),
                FeeStates.builder().id(4L).feeTypeId(2L).limit(200.0).price(BigDecimal.valueOf(1.5)).oilRate(0.2).state(3L).build(),
                FeeStates.builder().id(5L).feeTypeId(3L).limit(500.0).price(BigDecimal.valueOf(0.1)).oilRate(0.2).state(1L).build(),
                FeeStates.builder().id(6L).feeTypeId(3L).limit(800.0).price(BigDecimal.valueOf(0.12)).oilRate(0.2).state(2L).build(),
                FeeStates.builder().id(7L).feeTypeId(4L).limit(10.0).price(BigDecimal.valueOf(1.0)).oilRate(0.2).state(1L).build(),
                FeeStates.builder().id(8L).feeTypeId(4L).limit(15.0).price(BigDecimal.valueOf(1.2)).oilRate(0.2).state(2L).build()
        );
        int distance = 2020;  // 运输距离为120公里
        int weight = 2300;    // 货物重量为900kg
        int volume = 43;     // 货物体积为20立方米

        // 计算总运费
        BigDecimal totalCost = calculateShippingCost(distance, weight, volume, feeStatesList);
        System.out.println("总运费为: " + totalCost + " 元");

    }

}
