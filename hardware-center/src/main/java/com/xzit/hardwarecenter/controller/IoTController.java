package com.xzit.hardwarecenter.controller;

import com.xzit.common.hardware.model.dto.LimitDTO;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.hardwarecenter.service.IoTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class IoTController {
    private final IoTService ioTService;

    @GetMapping("/getCarArrangement/{carId}")
    Mono<ServerResponse<Long>> getArrangeId(@PathVariable Long carId){
        return Mono.just(ServerResponse.success(ioTService.getArrangementIdByCarId(carId)));
    }

}
