package com.xzit.api.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLogApplication.class, args);
    }

}
