package com.xzit.hardwarecenter.config;


import org.springframework.amqp.core.*;
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
    @Bean
    public Exchange maxwellExchange() {
        return new FanoutExchange("maxwell_exchange",true,false);
    }
    @Bean
    public Queue maxwellQueue() {
        return new Queue("maxwell_queue",true,false,true);
    }
    @Bean
    public Binding maxwell() {
        return new Binding("maxwell_queue", Binding.DestinationType.QUEUE,"maxwell_exchange","data-change",null);
    }
}
