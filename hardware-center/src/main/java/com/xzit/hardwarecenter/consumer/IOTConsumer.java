package com.xzit.hardwarecenter.consumer;

import com.xzit.common.hardware.entity.IOTData;
import com.xzit.hardwarecenter.mapper.IOTMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class IOTConsumer {
    private final IOTMapper iotMapper;
    private final List<IOTData> iotData=new ArrayList<>();
    @Bean
    public Consumer<IOTData> iot(){
        return iotData::add;
    }
    @Scheduled(fixedDelay = 60000)
    public void writeToDb(){
        if(!iotData.isEmpty()){
            iotMapper.batchInsert(iotData);
            iotData.clear();
        }
    }
}
