package com.xzit.hardwarecenter.handler;

import com.alibaba.fastjson2.JSON;
import com.xzit.api.logistics.feign.WarehouseFeignClient;
import com.xzit.common.hardware.model.dto.LimitDTO;
import com.xzit.common.logistics.entity.LimitTemp;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.common.hardware.model.vo.WarehouseDataVO;
import com.xzit.hardwarecenter.service.MqttSender;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseMessageHandlerImpl implements MessageHandler {
    private final RabbitTemplate rabbitTemplate;
    private final MqttSender mqttSender;
    private final WarehouseFeignClient warehouseFeignClient;
    @Override
    @ServiceActivator(inputChannel = "warehouseChannel")
    public void handleMessage(@NotNull Message<?> message) throws MessagingException {
        WarehouseDataVO warehouseDataVO= JSON.parseObject(message.getPayload().toString(),WarehouseDataVO.class);
        WarehouseData warehouseData= BeanCopyUtil.copyObject(warehouseDataVO,WarehouseData.class);
        LimitTemp limitTemp=warehouseFeignClient.getLimitTempById(warehouseData.getCenterId()).getData();
        LimitDTO limitDTO=BeanCopyUtil.copyObject(limitTemp,LimitDTO.class);
        mqttSender.sendToMqtt(JSON.toJSONString(limitDTO));
        rabbitTemplate.convertAndSend("warehouse-exchange","warehouse-create",warehouseData);

    }
}
