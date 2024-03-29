package com.xzit.api.logistics.feign;

import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "logistics-service",contextId = "courier")
public interface CourierFeignClient {
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/isBindCourier")
    ServerResponse<Boolean> isBindCourier(Long userInfoId);

}
