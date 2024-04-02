package com.xzit.logisticscenter;

import com.xzit.common.logistics.constant.LogisticConstant;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.model.vo.LocationResultVO;
import com.xzit.logisticscenter.mapper.AreaMapper;
import com.xzit.logisticscenter.service.LogisticService;
import com.xzit.logisticscenter.service.impl.LogisticServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LogisticsCenterApplicationTests {
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    LogisticServiceImpl logisticService;

    @Test
    void contextLoads() {
        Map<String,Double> mylocation=new HashMap<>();
        mylocation.put("latitude",34.193122);
        mylocation.put("longitude",117.301804);
        Map<String,Double> mylocation1=new HashMap<>();
        mylocation1.put("latitude",31.95262);
        mylocation1.put("longitude",118.812557);
        System.out.println(logisticService.getDistance(mylocation,mylocation1));
//        System.out.println(logisticService.test(10L,10L,"江苏省徐州市徐州工程学院","江苏省南京市鼓楼区中环国际广场",560.0,10.0));

    }






}
