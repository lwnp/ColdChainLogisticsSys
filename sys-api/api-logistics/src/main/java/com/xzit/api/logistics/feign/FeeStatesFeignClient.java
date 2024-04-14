package com.xzit.api.logistics.feign;

import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "logistics-service",contextId = "fee")
public interface FeeStatesFeignClient {
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/getAllFeeStates")
    ServerResponse<List<FeeStates>> getAllFeeStates();

}
