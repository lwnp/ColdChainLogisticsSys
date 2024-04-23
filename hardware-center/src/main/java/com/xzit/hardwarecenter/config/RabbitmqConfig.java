package com.xzit.hardwarecenter.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    @Bean
    public Queue wareHouseQueue() {
        return new Queue("warehouse-queue",true,false,true);
    }
    @Bean
    public Exchange wareHouseExchange() {
        return new DirectExchange("warehouse-exchange",true,false);
    }
    @Bean
    public Binding wareHouse() {
        return new Binding("warehouse-queue", Binding.DestinationType.QUEUE,"warehouse-exchange","warehouse-create",null);
    }
}
