package com.xzit.sysauthorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.xzit.api.user"})
public class SysAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysAuthorizationApplication.class, args);
    }

}
