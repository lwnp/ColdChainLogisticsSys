package com.xzit.logcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.xzit"})
@EnableFeignClients(basePackages = {"com.xzit.api.**.feign"})
public class LogCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogCenterApplication.class, args);
    }

}
