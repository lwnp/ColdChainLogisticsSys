package com.xzit.logisticscenter;

import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.logisticscenter.mapper.AreaMapper;
import com.xzit.logisticscenter.mapper.FeeStatesMapper;
import com.xzit.logisticscenter.service.impl.LogisticServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LogisticsCenterApplicationTests {
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    LogisticServiceImpl logisticService;
    @Autowired
    FeeStatesMapper statesMapper;

    @Test
    void contextLoads() {
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
        for (FeeStates feeStates : feeStatesList) {
            statesMapper.insert(feeStates);
        }

    }






}
