package com.xzit.hardwarecenter;

import co.elastic.clients.util.DateTime;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xzit.common.hardware.entity.IOTData;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.sys.entity.Notice;
import com.xzit.hardwarecenter.repository.WarehouseDataRepository;
import com.xzit.hardwarecenter.service.MqttSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class HardwareCenterApplicationTests {
    @Autowired
    WarehouseDataRepository warehouseDataRepository;
    @Autowired
    StreamBridge streamBridge;
    @Autowired
    MqttSender mqttSender;

    @Test
    void contextLoads() {
        List<WarehouseData> warehouses =  warehouseDataRepository.getAllByCenterIdAndCreateTimeIsAfter(1L,new Date(1622505600000L));
        System.out.println(warehouses);
//        warehouseDataRepository.save(WarehouseData.builder().id(1L).centerId(1L).oxide(100.0).build());

    }
    @Test
    void reserve(){
        String test="{\"centerid\": \"002\", \"min\": -20,\"max\": 30,\"switch\": 1}";
        mqttSender.sendToMqtt(test);

    }
    @Test
    void test(){
        Map<String,Object> map = new HashMap<>();
        map.put("centerid",String.format("%03d",1));
        map.put("min",20.1);
        map.put("max",30.1);
        map.put("switch",1);
        JSONObject jsonObject = new JSONObject(map);
        System.out.println(jsonObject);
        System.out.println(JSON.toJSONString(jsonObject));
    }




}
