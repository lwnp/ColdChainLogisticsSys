package com.xzit.hardwarecenter.handler;

import com.alibaba.fastjson2.JSON;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.common.hardware.model.vo.WarehouseDataVO;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseMessageHandlerImpl implements MessageHandler {
    private final RabbitTemplate rabbitTemplate;
    @Override
    @ServiceActivator(inputChannel = "warehouseChannel")
    public void handleMessage(@NotNull Message<?> message) throws MessagingException {
        WarehouseDataVO warehouseDataVO= JSON.parseObject(message.getPayload().toString(),WarehouseDataVO.class);
        WarehouseData warehouseData= BeanCopyUtil.copyObject(warehouseDataVO,WarehouseData.class);
        rabbitTemplate.convertAndSend("warehouse-exchange","warehouse-create",warehouseData);

    }
}
