package com.xzit.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserCenterApplicationTests {


    @Test
    void contextLoads() {
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));

    }

}
