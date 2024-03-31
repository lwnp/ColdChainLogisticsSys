package com.xzit.ordercenter.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.msg.AlipayMsgClient;
import com.alipay.api.msg.MsgHandler;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.xzit.common.order.constant.AlipayConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AlipayConfiguration {
    private final MsgHandler msgHandler;
    @Value("${alipay.notify}")
    private String notifyUrl;
    @Value("${alipay.redirect}")
    private String redirectUrl;
    @Bean
    public AlipayClient alipayClient() throws AlipayApiException {
        AlipayConfig  config = new AlipayConfig();
        config.setAlipayPublicKey(AlipayConstant.PUBLIC_KEY);
        config.setAppId(AlipayConstant.APPID);
        config.setCharset(AlipayConstant.CHARSET);
        config.setFormat(AlipayConstant.FORMAT);
        config.setPrivateKey(AlipayConstant.PRIVATE_KEY);
        config.setSignType(AlipayConstant.SIGN_TYPE);
        config.setServerUrl(AlipayConstant.URL);
        return new DefaultAlipayClient(config);
    }
    @Bean
    public AlipayMsgClient alipayMsgClient() throws Exception {
        final  AlipayMsgClient alipayMsgClient = AlipayMsgClient.getInstance(AlipayConstant.APPID);
        alipayMsgClient.setConnector(AlipayConstant.SERVER_HOST);
        alipayMsgClient.setMessageHandler(msgHandler);
        alipayMsgClient.setSecurityConfig(AlipayConstant.SIGN_TYPE, AlipayConstant.PRIVATE_KEY, AlipayConstant.PUBLIC_KEY);
        return alipayMsgClient;
    }
    @Bean
    public AlipayTradePagePayRequest alipayTradePagePayRequest(){
        AlipayTradePagePayRequest request=new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(redirectUrl);
        return request;
    }

}
