package com.xzit.hardwarecenter;

import com.alibaba.fastjson2.JSON;
import com.xzit.common.hardware.entity.IOTData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HardwareCenterApplicationTests {


    @Test
    void contextLoads() {

    }
    @Test
    void reserve(){
        String test="{\"carId\":1,\"carbonDioxide\":400.0,\"id\":1,\"location\":{\"latitude\":45.154124,\"longitude\":81.222415},\"timeStamp\":\"2024-03-18 22:24:02.400268200\"}";
        IOTData sensorData=JSON.parseObject(test, IOTData.class);
        System.out.println(sensorData);

    }


}
