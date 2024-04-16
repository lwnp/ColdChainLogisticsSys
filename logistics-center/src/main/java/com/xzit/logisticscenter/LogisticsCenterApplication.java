package com.xzit.logisticscenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.xzit"})
@EnableFeignClients(basePackages = {"com.xzit.api.**.feign"})
@EnableDiscoveryClient
public class LogisticsCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsCenterApplication.class, args);
    }

}
