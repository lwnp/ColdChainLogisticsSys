package com.xzit.logisticscenter;

import com.xzit.common.logistics.constant.LogisticConstant;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.model.vo.LocationResultVO;
import com.xzit.logisticscenter.mapper.AreaMapper;
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

    @Test
    void contextLoads() {
        String address="江苏省徐州市徐州工程学院";
        WebClient webClient= WebClient.create();
        Mono<ResponseEntity<LocationResultVO>> result=webClient.get()
                .uri(LogisticConstant.ADDRESS_TO_LOCATION_API,address,LogisticConstant.APP_KEY)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(LocationResultVO.class);

        ResponseEntity<LocationResultVO> responseEntity = result.block(); // 阻塞等待结果
        assertNotNull(responseEntity, "Response entity should not be null");
        assertNotNull(responseEntity.getBody(), "Response body should not be null");

        // 这里可以打印responseEntity等你想要的操作
        System.out.println(responseEntity.getBody().getResult());

    }

}
