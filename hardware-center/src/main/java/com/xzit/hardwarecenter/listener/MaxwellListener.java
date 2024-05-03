package com.xzit.hardwarecenter.listener;

import com.alibaba.fastjson2.JSON;
import com.rabbitmq.client.Channel;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.common.hardware.model.dto.MaxwellDataDTO;
import com.xzit.hardwarecenter.repository.WarehouseDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MaxwellListener {
    private final WarehouseDataRepository warehouseDataRepository;

    @RabbitListener(queues = "maxwell_queue")
    public void maxwellData(byte[] data, Channel channel, Message message) throws IOException {
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(data, MaxwellDataDTO.class);
        WarehouseData warehouseData = JSON.parseObject(JSON.toJSONBytes(maxwellDataDTO.getData()), WarehouseData.class);
        switch(maxwellDataDTO.getType()){
            case "insert", "update":
                warehouseDataRepository.save(warehouseData);
                break;
            case "delete":
                warehouseDataRepository.deleteById(warehouseData.getId());
                break;
            default:
                break;
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
