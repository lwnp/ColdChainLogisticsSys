package com.xzit.logisticscenter;

import com.xzit.common.logistics.entity.Area;
import com.xzit.logisticscenter.mapper.AreaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class LogisticsCenterApplicationTests {
    @Autowired
    AreaMapper areaMapper;

    @Test
    void contextLoads() {
        List<Area> areaList = new ArrayList<>();
        areaList.add(new Area(null, "北京市"));
        areaList.add(new Area(null, "天津市"));
        areaList.add(new Area(null, "上海市"));
        areaList.add(new Area(null, "重庆市"));
        areaList.add(new Area(null, "河北省"));
        areaList.add(new Area(null, "山西省"));
        areaList.add(new Area(null, "辽宁省"));
        areaList.add(new Area(null, "吉林省"));
        areaList.add(new Area(null, "黑龙江省"));
        areaList.add(new Area(null, "江苏省"));
        areaList.add(new Area(null, "浙江省"));
        areaList.add(new Area(null, "安徽省"));
        areaList.add(new Area(null, "福建省"));
        areaList.add(new Area(null, "江西省"));
        areaList.add(new Area(null, "山东省"));
        areaList.add(new Area(null, "河南省"));
        areaList.add(new Area(null, "湖北省"));
        areaList.add(new Area(null, "湖南省"));
        areaList.add(new Area(null, "广东省"));
        areaList.add(new Area(null, "海南省"));
        areaList.add(new Area(null, "四川省"));
        areaList.add(new Area(null, "贵州省"));
        areaList.add(new Area(null, "云南省"));
        areaList.add(new Area(null, "陕西省"));
        areaList.add(new Area(null, "甘肃省"));
        areaList.add(new Area(null, "青海省"));
        areaList.add(new Area(null, "台湾省"));
        areaList.add(new Area(null, "内蒙古自治区"));
        areaList.add(new Area(null, "广西壮族自治区"));
        areaList.add(new Area(null, "西藏自治区"));
        areaList.add(new Area(null, "宁夏回族自治区"));
        areaList.add(new Area(null, "新疆维吾尔自治区"));
        areaList.add(new Area(null, "香港特别行政区"));
        areaList.add(new Area(null, "澳门特别行政区"));
        for (Area area:areaList){
            areaMapper.insert(area);
        }
    }

}
