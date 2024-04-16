package com.xzit.ordercenter.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {
    @Bean
    public Queue orderDelayQueue() {
        Map<String,Object> arguments = new HashMap<>();
        //死信路由
        arguments.put("x-dead-letter-exchange","order-exchange");
        //死信路由键
        arguments.put("x-dead-letter-routing-key","order-release");
        //消息过期时间、单位：毫秒
        arguments.put("x-message-ttl",300000);
        return new Queue("order-delay-queue",true,false,false,arguments);
    }

    @Bean
    public Queue orderReleaseOrderQueue() {
        return new Queue("order-release-queue",true,false,false);
    }

    @Bean
    public Exchange orderExchange() {
        return new TopicExchange("order-exchange",true,false);
    }

    @Bean
    public Binding orderCreate() {

        return new Binding("order-delay-queue",Binding.DestinationType.QUEUE,"order-exchange","order-create",null);
    }

    @Bean
    public Binding orderRelease() {
        return new Binding("order-release-queue",Binding.DestinationType.QUEUE,"order-exchange","order-release",null);
    }

}
