package com.xzit.logisticscenter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.common.logistics.entity.LogisticFlow;
import com.xzit.logisticscenter.mapper.*;
import com.xzit.logisticscenter.repository.LimitTempRepository;
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
    @Autowired
    LimitTempRepository limitTempRepository;
    @Autowired
    CourierMapper courierMapper;

    @Test
    void contextLoads() {

    }






}
