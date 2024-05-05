package com.xzit.api.logistics.feign;

import com.xzit.common.logistics.entity.LimitTemp;
import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "logistics-service",contextId = "warehouse")
public interface WarehouseFeignClient {
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/getCenterIdByOrderNum")
    ServerResponse<Long> getCenterIdByOrderNum(@RequestParam("orderNum") String orderNum);
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/getLimitTempById")
    ServerResponse<LimitTemp> getLimitTempById(@RequestParam("id") Long id);
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/getLimitTempList")
    ServerResponse<List<LimitTemp>> getLimitTempList();
}
