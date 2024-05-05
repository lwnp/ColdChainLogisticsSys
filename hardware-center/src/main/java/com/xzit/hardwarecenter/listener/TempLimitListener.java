package com.xzit.hardwarecenter.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xzit.api.logistics.feign.WarehouseFeignClient;
import com.xzit.common.hardware.model.dto.LimitDTO;
import com.xzit.common.logistics.entity.LimitTemp;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.hardwarecenter.service.MqttSender;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TempLimitListener {
    private final WarehouseFeignClient warehouseFeignClient;
    private final MqttSender mqttSender;

    @Scheduled(fixedRate = 300000)
    public void broadcast() {
        List<LimitTemp> tempLimits = warehouseFeignClient.getLimitTempList().getData();
        for (LimitTemp tempLimit : tempLimits) {
            LimitDTO limitDTO = BeanCopyUtil.copyObject(tempLimit, LimitDTO.class);
            Map<String,Object> map = new HashMap<>();
            map.put("centerid",String.format("%03d",tempLimit.getCenterId()));
            map.put("min",limitDTO.getMin());
            map.put("max",limitDTO.getMax());
            map.put("switch",1);
            JSONObject jsonObject = new JSONObject(map);
            mqttSender.sendToMqtt(JSONObject.toJSONString(jsonObject));
        }

    }
}
