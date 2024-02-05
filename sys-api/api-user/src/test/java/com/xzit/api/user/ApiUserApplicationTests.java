package com.xzit.api.user;

import com.xzit.api.user.feign.UserFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootTest
class ApiUserApplicationTests {
    @Autowired
    UserFeignClient client;

    @Test
    void contextLoads() {
        System.out.println(client.getUserDetailsByUsername("marsh"));
    }

}
