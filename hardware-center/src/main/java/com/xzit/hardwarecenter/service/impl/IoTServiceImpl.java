package com.xzit.hardwarecenter.service.impl;

import com.xzit.api.logistics.feign.ArrangementFeignClient;
import com.xzit.hardwarecenter.service.IoTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IoTServiceImpl implements IoTService {
    private final ArrangementFeignClient arrangementFeignClient;
    @Override
    public Long getArrangementIdByCarId(Long carId) {
        return arrangementFeignClient.getArrangementIdByCarId(carId).getData();
    }
}
