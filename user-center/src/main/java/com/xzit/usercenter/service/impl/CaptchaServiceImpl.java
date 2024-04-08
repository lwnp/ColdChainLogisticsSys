package com.xzit.usercenter.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.xzit.common.sys.constant.EmailConstant;
import com.xzit.common.sys.constant.MQConstant;
import com.xzit.common.sys.model.dto.EmailDTO;
import com.xzit.common.sys.model.vo.EmailVO;
import com.xzit.usercenter.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {
    private final RedisTemplate<String,Object> redisTemplate;
    private final StreamBridge streamBridge;
    private String generateCaptcha(String email) {
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
    public void sendCaptchaToExchange(EmailVO emailVO) {
        String key=generateCaptcha(emailVO.getEmail());
        EmailDTO emailDTO=EmailDTO.builder()
                .email(emailVO.getEmail())
                .content("您的验证码为 " + key + " 有效期15分钟，请不要告诉他人哦！")
                .template("common.html")
                .subject(EmailConstant.EMAIL_SUBJECT)
                .build();
        Message<EmailDTO> message= MessageBuilder.withPayload(emailDTO).build();
        streamBridge.send(MQConstant.EMAIL_EXCHANGE,message);
    }


}
