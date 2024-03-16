package com.xzit.hardwarecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"com.xzit"})
public class HardwareCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HardwareCenterApplication.class, args);
    }

}
