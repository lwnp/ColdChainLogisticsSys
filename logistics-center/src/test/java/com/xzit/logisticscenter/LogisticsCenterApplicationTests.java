package com.xzit.logisticscenter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.common.logistics.entity.LogisticFlow;
import com.xzit.logisticscenter.mapper.AreaMapper;
import com.xzit.logisticscenter.mapper.ArrangementMapper;
import com.xzit.logisticscenter.mapper.FeeStatesMapper;
import com.xzit.logisticscenter.mapper.LogisticFlowMapper;
import com.xzit.logisticscenter.service.impl.LogisticServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    ArrangementMapper arrangementMapper;
    @Autowired
    LogisticFlowMapper logisticFlowMapper;

    @Test
    void contextLoads() {
        List<String> testData = new ArrayList<>();
        testData.add("110000");
        testData.add("110100");
        testData.add("110101");
        LogisticFlow logisticFlow = LogisticFlow.builder()
                .orderNum("123456")
                .description("测试数据")
                .weight(10.0)
                .images(testData)
                .build();
        logisticFlowMapper.insert(logisticFlow);

    }






}
