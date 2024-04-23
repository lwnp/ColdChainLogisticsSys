package com.xzit.api.hardware.feign;

import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.hardware.model.dto.WarehouseDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "hardware-service",contextId = "warehouse")
public interface WarehouseFeignClient {
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/hardware/getWarehouseData")
    ServerResponse<List<WarehouseDataDTO>> getWarehouseData(@RequestParam("orderNum") String orderNum);
}
