package com.xzit.ordercenter.listener;

import com.alipay.api.AlipayApiException;
import com.rabbitmq.client.Channel;
import com.xzit.common.order.entity.Order;
import com.xzit.ordercenter.mapper.OrderMapper;
import com.xzit.ordercenter.service.AlipayService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OrderListener {
    private final OrderMapper orderMapper;
    private final AlipayService alipayService;

    @RabbitListener(queues = "order-delay-queue")
    public void payOrder(Order order, Channel channel, Message message) throws IOException, AlipayApiException {
        if(alipayService.isPaid(order.getOrderNum())) {
            order.setIsPay(true);
            orderMapper.updateById(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            // 消息重新放回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    @RabbitListener(queues = "order-release-queue")
    public void deadOrder(Order order, Channel channel, Message message) throws IOException {
        try {
            order.setIsActive(false);
            orderMapper.updateById(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
