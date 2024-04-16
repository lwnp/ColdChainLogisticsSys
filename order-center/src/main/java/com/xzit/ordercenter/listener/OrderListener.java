package com.xzit.ordercenter.listener;

import com.alipay.api.AlipayApiException;
import com.rabbitmq.client.Channel;
import com.xzit.api.logistics.feign.ArrangementFeignClient;
import com.xzit.common.order.entity.Order;
import com.xzit.common.sys.constant.MQConstant;
import com.xzit.common.sys.entity.Notice;
import com.xzit.ordercenter.mapper.OrderMapper;
import com.xzit.ordercenter.service.AlipayService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OrderListener {
    private final OrderMapper orderMapper;
    private final AlipayService alipayService;
    private final ArrangementFeignClient arrangementFeignClient;
    private final StreamBridge streamBridge;

    @RabbitListener(queues = "order-delay-queue")
    public void payOrder(Order order, Channel channel, Message message) throws IOException {
        if(alipayService.isPaid(order.getOrderNum())) {
            order.setIsPay(true);
            orderMapper.updateById(order);
            Notice notice= Notice.builder()
                    .title(String.format(MQConstant.USER_PAID_TITLE, order.getOrderNum()))
                    .content(MQConstant.USER_PAID_CONTENT)
                    .isAdminMessage(true)
                    .userInfoId(order.getUserInfoId())
                    .build();
            streamBridge.send(MQConstant.NOTICE_EXCHANGE, MessageBuilder.withPayload(notice).build());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            // 消息重新放回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    @RabbitListener(queues = "order-release-queue")
    public void deadOrder(Order order, Channel channel, Message message) throws IOException {
        try {
            arrangementFeignClient.recoverArrange(order.getOrderNum());
            order.setIsActive(false);
            orderMapper.updateById(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
