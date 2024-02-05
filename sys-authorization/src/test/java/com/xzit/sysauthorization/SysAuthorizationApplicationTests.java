package com.xzit.sysauthorization;

import com.xzit.api.user.feign.UserFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootTest
class SysAuthorizationApplicationTests {
    @Autowired
    UserFeignClient userFeignClient;

    @Test
    void contextLoads() {

    }

}
