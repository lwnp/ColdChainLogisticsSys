package com.xzit.logisticscenter;

import com.xzit.common.logistics.constant.LogisticConstant;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.model.vo.LocationResultVO;
import com.xzit.logisticscenter.mapper.AreaMapper;
import com.xzit.logisticscenter.service.LogisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LogisticsCenterApplicationTests {
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    LogisticService logisticService;

    @Test
    void contextLoads() {

    }






}
