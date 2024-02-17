package com.xzit.sysauthorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = {"com.xzit.sysauthorization","com.xzit.api.user.feign","com.xzit.common"})
@EnableFeignClients(basePackages = {"com.xzit.api.**.feign"})
public class SysAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysAuthorizationApplication.class, args);
    }

}
