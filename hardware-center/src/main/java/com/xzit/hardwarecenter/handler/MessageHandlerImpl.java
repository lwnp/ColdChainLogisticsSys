package com.xzit.hardwarecenter.handler;

import com.alibaba.fastjson2.JSON;
import com.xzit.common.sys.constant.MQConstant;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.hardware.entity.IOTData;
import com.xzit.common.hardware.model.vo.IOTDataVO;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {
    private final StreamBridge streamBridge;
    @Override
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(@NotNull Message<?> message) throws MessagingException {
        IOTDataVO dataVO = JSON.parseObject(message.getPayload().toString(),IOTDataVO.class);
        IOTData data= BeanCopyUtil.copyObject(dataVO,IOTData.class);
        Message<IOTData> iotDataMessage= MessageBuilder.withPayload(data).build();
        streamBridge.send(MQConstant.IOT_EXCHANGE, iotDataMessage);

    }

}
