package com.xzit.ordercenter;

import com.alipay.api.msg.AlipayMsgClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.xzit"})
@EnableFeignClients(basePackages = {"com.xzit.api.**.feign"})
@EnableDiscoveryClient
@RequiredArgsConstructor
public class OrderCenterApplication {
    private final AlipayMsgClient alipayMsgClient;
    @PostConstruct
    public void connectAlipayMessageServer() throws InterruptedException {
        alipayMsgClient.connect();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderCenterApplication.class, args);
    }

}
