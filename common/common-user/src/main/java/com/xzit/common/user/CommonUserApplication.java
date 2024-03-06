package com.xzit.common.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CommonUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonUserApplication.class, args);
    }

}
