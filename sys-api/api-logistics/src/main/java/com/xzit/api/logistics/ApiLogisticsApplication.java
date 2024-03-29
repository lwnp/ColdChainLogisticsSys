package com.xzit.api.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiLogisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLogisticsApplication.class, args);
    }

}
