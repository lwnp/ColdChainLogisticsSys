package com.xzit.usercenter.service;

public interface CaptchaService {
    String generateCaptcha(String email);
    Boolean checkCaptcha(String email,String code);
    void sendCaptcha();
}
