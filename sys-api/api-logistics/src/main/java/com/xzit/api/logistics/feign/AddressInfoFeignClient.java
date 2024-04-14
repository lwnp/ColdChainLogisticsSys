package com.xzit.api.logistics.feign;

import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.model.dto.AddressInfoDTO;
import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "logistics-service",contextId = "address")
public interface AddressInfoFeignClient {
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/getAddressInfoDTO")
    ServerResponse<AddressInfoDTO> getAddressInfoDTO(@RequestParam(value = "addressId") Long addressId);
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/getAddressInfo")
    ServerResponse<AddressInfo>  getAddressInfo(@RequestParam(value = "addressId") Long addressId);
}
