package com.xzit.api.user.feign;

import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user-service",contextId = "user-role")
public interface RoleFeignClient {
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/user/getRoleNameById")
    ServerResponse<String> getRoleNameById(Long id);
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/user/listUserRoles")
    ServerResponse<List<String>> listUserRoles(@RequestParam("username") String username);
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/user/isValidCourier")
    ServerResponse<Boolean> isValidCourier(@RequestParam("userInfoId") Long userInfoId);
}
