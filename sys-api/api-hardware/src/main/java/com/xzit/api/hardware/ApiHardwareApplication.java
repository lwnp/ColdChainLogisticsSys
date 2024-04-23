package com.xzit.api.hardware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiHardwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiHardwareApplication.class, args);
    }

}
