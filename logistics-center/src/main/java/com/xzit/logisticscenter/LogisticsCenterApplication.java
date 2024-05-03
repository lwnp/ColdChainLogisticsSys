package com.xzit.logisticscenter;

import com.xzit.common.logistics.entity.LimitTemp;
import com.xzit.logisticscenter.mapper.LimitTempMapper;
import com.xzit.logisticscenter.repository.LimitTempRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.xzit"})
@EnableFeignClients(basePackages = {"com.xzit.api.**.feign"})
@EnableDiscoveryClient
@RequiredArgsConstructor
public class LogisticsCenterApplication {
    private final LimitTempRepository limitTempRepository;
    private final LimitTempMapper limitTempMapper;
    @PostConstruct
    public void init(){
        limitTempRepository.deleteAll();
        List<LimitTemp> limitTemps = limitTempMapper.selectList(null);
        limitTempRepository.saveAll(limitTemps);
    }

    public static void main(String[] args) {
        SpringApplication.run(LogisticsCenterApplication.class, args);
    }

}
