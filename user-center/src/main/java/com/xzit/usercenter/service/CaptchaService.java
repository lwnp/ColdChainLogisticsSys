package com.xzit.usercenter.service;

import com.xzit.common.sys.model.vo.EmailVO;

public interface CaptchaService {
    Boolean checkCaptcha(String email,String code);
    void sendCaptchaToExchange(EmailVO emailVO);
}
