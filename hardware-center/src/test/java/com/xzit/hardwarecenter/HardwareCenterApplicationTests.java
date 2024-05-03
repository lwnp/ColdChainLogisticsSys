package com.xzit.hardwarecenter;

import co.elastic.clients.util.DateTime;
import com.alibaba.fastjson2.JSON;
import com.xzit.common.hardware.entity.IOTData;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.sys.entity.Notice;
import com.xzit.hardwarecenter.repository.WarehouseDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@SpringBootTest
class HardwareCenterApplicationTests {
    @Autowired
    WarehouseDataRepository warehouseDataRepository;
    @Autowired
    StreamBridge streamBridge;

    @Test
    void contextLoads() {
        List<WarehouseData> warehouses =  warehouseDataRepository.getAllByCenterIdAndCreateTimeIsAfter(1L,new Date(1622505600000L));
        System.out.println(warehouses);
//        warehouseDataRepository.save(WarehouseData.builder().id(1L).centerId(1L).oxide(100.0).build());

    }
    @Test
    void reserve(){
        String test="{\"carId\":1,\"carbonDioxide\":400.0,\"id\":1,\"location\":{\"latitude\":45.154124,\"longitude\":81.222415},\"timeStamp\":\"2024-03-18 22:24:02.400268200\"}";
        IOTData sensorData=JSON.parseObject(test, IOTData.class);
        System.out.println(sensorData);

    }
    @Test
    void test(){
        Notice notice=Notice.builder().title("测试").content("测试").isAlarm(true).createTime(LocalDateTime.now()).build();
        streamBridge.send("notice-out-0", MessageBuilder.withPayload(notice).build());
    }




}
