package com.xzit.usercenter;

import com.xzit.common.user.entity.UserInfo;
import com.xzit.usercenter.mapper.UserInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = UserCenterApplication.class)
class UserCenterApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;


    @Test
    void contextLoads() {
        UserInfo user=UserInfo.builder().email("2300362038@qq.com").avatar("***").nickname("funny").build();
        System.out.println(userInfoMapper.insertUserInfo(user));

    }

}
