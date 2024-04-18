package com.xzit.hardwarecenter;

import com.xzit.common.sys.config.SpringWebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.xzit"})
@EnableFeignClients(basePackages = {"com.xzit.api.**.feign"})
@EnableScheduling
public class HardwareCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HardwareCenterApplication.class, args);
    }

}
