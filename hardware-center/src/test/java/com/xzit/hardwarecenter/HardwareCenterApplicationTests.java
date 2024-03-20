package com.xzit.hardwarecenter;

import com.alibaba.fastjson2.JSON;
import com.xzit.commonhardware.entity.SensorData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class HardwareCenterApplicationTests {


    @Test
    void contextLoads() {
        Map<String,Double> map=new HashMap<>();
        map.put("longitude",81.222415);
        map.put("latitude",45.154124);
        SensorData sensorData= SensorData.builder()
                .id(1L)
                .carId(1L)
                .timeStamp(LocalDateTime.now())
                .carbonDioxide(400.0)
                .location(map)
                .build();
        System.out.println(JSON.toJSONString(sensorData));
    }
    @Test
    void reserve(){
        String test="{\"carId\":1,\"carbonDioxide\":400.0,\"id\":1,\"location\":{\"latitude\":45.154124,\"longitude\":81.222415},\"timeStamp\":\"2024-03-18 22:24:02.400268200\"}";
        SensorData sensorData=JSON.parseObject(test, SensorData.class);
        System.out.println(sensorData);

    }


}
