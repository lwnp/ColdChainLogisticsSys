package com.xzit.sysgateway;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableKnife4j
public class SysGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysGatewayApplication.class, args);
    }

}
