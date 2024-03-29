package com.xzit.logisticscenter.feign;

import com.xzit.api.logistics.feign.CourierFeignClient;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.logisticscenter.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourierFeignController implements CourierFeignClient {
    private final CourierService courierService;
    @Override
    public ServerResponse<Boolean> isBindCourier(Long userInfoId) {
        return ServerResponse.success(courierService.isBindCourier(userInfoId));
    }
}
