package com.xzit.hardwarecenter.listener;

import com.xzit.api.logistics.feign.WarehouseFeignClient;
import com.xzit.api.order.feign.GoodsFeignClient;
import com.xzit.api.order.feign.OrderFeignClient;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.order.entity.Order;
import com.xzit.common.sys.constant.MQConstant;
import com.xzit.common.sys.entity.Notice;
import com.xzit.hardwarecenter.repository.WarehouseDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AlarmListener {
    private final StreamBridge streamBridge;
    private final WarehouseDataRepository warehouseDataRepository;
    private final OrderFeignClient orderFeignClient;
    private final GoodsFeignClient goodsFeignClient;
    private final WarehouseFeignClient warehouseFeignClient;

    @Scheduled(fixedDelay = 600000)
    public void scanData(){
        List<Order> orderList = orderFeignClient.getInStoreOrder().getData();
        long timestamp = new Date().getTime();
        long startTime = timestamp - 600000;
        warehouseDataRepository.deleteAllByCreateTimeIsBefore(new Date(startTime));
        if(orderList != null && !orderList.isEmpty()){
            for(Order order : orderList){
                Long centerId= warehouseFeignClient.getCenterIdByOrderNum(order.getOrderNum()).getData();
                Goods goods = goodsFeignClient.getGoodsByOrderNum(order.getOrderNum()).getData();
                List<WarehouseData> warehouseData=warehouseDataRepository.getAllByCenterIdAndCreateTimeIsAfter(centerId,new Date(startTime));
                for (WarehouseData scanData : warehouseData){
                    if(scanData.getHumidity() > goods.getMaxHumidity() || scanData.getHumidity() < goods.getMinHumidity()
                            || scanData.getTemperature() > goods.getMaxTemperature() || scanData.getTemperature() < goods.getMinTemperature()
                            || scanData.getDioxide() > goods.getMaxDioxide() || scanData.getDioxide() < goods.getMinDioxide()
                            || scanData.getMethane() > goods.getMaxMethane() || scanData.getMethane() < goods.getMinMethane()
                            || scanData.getOxide() > goods.getMaxOxide() || scanData.getOxide() < goods.getMinOxide()){
                        Notice notice = Notice.builder()
                                .isAlarm(true)
                                .isAdminMessage(true)
                                .userInfoId(goods.getUserInfoId())
                                .title(MQConstant.ALARM_TITLE)
                                        .content(MQConstant.ALARM_CONTENT.formatted(order.getOrderNum(),centerId))
                                                .build();
                        Notice userNotice = Notice.builder()
                                .isAlarm(true)
                                .isAdminMessage(false)
                                .userInfoId(goods.getUserInfoId())
                                .title(MQConstant.ALARM_TITLE)
                                .content(MQConstant.ALARM_USER_CONTENT.formatted(order.getOrderNum()))
                                .build();
                        streamBridge.send(MQConstant.NOTICE_EXCHANGE, MessageBuilder.withPayload(notice).build());
                        streamBridge.send(MQConstant.NOTICE_EXCHANGE, MessageBuilder.withPayload(userNotice).build());
                        break;
                    }
                }
            }
        }

    }
}
