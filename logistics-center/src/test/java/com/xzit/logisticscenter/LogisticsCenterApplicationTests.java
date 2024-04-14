package com.xzit.logisticscenter;

import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.logisticscenter.mapper.AreaMapper;
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

    @Test
    void contextLoads() {
        List<Area> areas = new ArrayList<>();
        areas.add(new Area(11L, "北京市"));
        areas.add(new Area(12L, "天津市"));
        areas.add(new Area(13L, "河北省"));
        areas.add(new Area(14L, "山西省"));
        areas.add(new Area(15L, "内蒙古自治区"));
        areas.add(new Area(21L, "辽宁省"));
        areas.add(new Area(22L, "吉林省"));
        areas.add(new Area(23L, "黑龙江省"));
        areas.add(new Area(31L, "上海市"));
        areas.add(new Area(32L, "江苏省"));
        areas.add(new Area(33L, "浙江省"));
        areas.add(new Area(34L, "安徽省"));
        areas.add(new Area(35L, "福建省"));
        areas.add(new Area(36L, "江西省"));
        areas.add(new Area(37L, "山东省"));
        areas.add(new Area(41L, "河南省"));
        areas.add(new Area(42L, "湖北省"));
        areas.add(new Area(43L, "湖南省"));
        areas.add(new Area(44L, "广东省"));
        areas.add(new Area(45L, "广西壮族自治区"));
        areas.add(new Area(46L, "海南省"));
        areas.add(new Area(50L, "重庆市"));
        areas.add(new Area(51L, "四川省"));
        areas.add(new Area(52L, "贵州省"));
        areas.add(new Area(53L, "云南省"));
        areas.add(new Area(54L, "西藏自治区"));
        areas.add(new Area(61L, "陕西省"));
        areas.add(new Area(62L, "甘肃省"));
        areas.add(new Area(63L, "青海省"));
        areas.add(new Area(64L, "宁夏回族自治区"));
        areas.add(new Area(65L, "新疆维吾尔自治区"));
        for (Area a:areas){
            areaMapper.insert(a);
        }

    }






}
