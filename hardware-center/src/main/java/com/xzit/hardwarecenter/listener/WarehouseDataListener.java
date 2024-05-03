package com.xzit.hardwarecenter.listener;

import com.rabbitmq.client.Channel;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.hardwarecenter.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WarehouseDataListener {
    private final WarehouseMapper warehouseMapper;
    private final List<WarehouseData> warehouseDataList=new ArrayList<>();
    @RabbitListener(queues = "warehouse-queue")
    public void warehouseDataStore(WarehouseData warehouseData, Channel channel, Message message) throws IOException {
        warehouseDataList.add(warehouseData);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
    @Scheduled(fixedDelay = 60000)
    public void writeToDb(){
        if(!warehouseDataList.isEmpty()){
            warehouseMapper.batchInsert(warehouseDataList);
            warehouseDataList.clear();
        }
    }

}
