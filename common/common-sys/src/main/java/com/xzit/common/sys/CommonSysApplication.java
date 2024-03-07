package com.xzit.common.sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CommonSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonSysApplication.class, args);
    }

}
