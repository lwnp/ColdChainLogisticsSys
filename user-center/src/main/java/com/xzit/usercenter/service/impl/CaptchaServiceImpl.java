package com.xzit.usercenter.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.xzit.usercenter.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    @Qualifier("myRedis")
    RedisTemplate<String,Object> redisTemplate;
    @Override
    public String generateCaptcha(String email) {
        String code= RandomUtil.randomString(5);
        redisTemplate.opsForValue().getAndDelete(email);
        redisTemplate.opsForValue().set(email,code,15, TimeUnit.MINUTES);
        return code;
    }

    @Override
    public Boolean checkCaptcha(String email, String code) {
        String value= (String) redisTemplate.opsForValue().get(email);
        return value != null && value.equals(code);
    }

    @Override
    public void sendCaptcha() {

    }


}
