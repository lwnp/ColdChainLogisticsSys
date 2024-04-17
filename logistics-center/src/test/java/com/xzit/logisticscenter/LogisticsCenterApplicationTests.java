package com.xzit.logisticscenter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.logisticscenter.mapper.AreaMapper;
import com.xzit.logisticscenter.mapper.ArrangementMapper;
import com.xzit.logisticscenter.mapper.FeeStatesMapper;
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

    @Test
    void contextLoads() {
        List<Arrangement> arrangementList=arrangementMapper.selectList(new QueryWrapper<Arrangement>().eq("order_num","orderNum").and(q->q.eq("step_id",5)));
        System.out.println(arrangementList);

    }






}
