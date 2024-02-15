package com.xzit.api.user.feign;

import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "user-service",contextId = "user-role")
public interface RoleFeignClient {
    @GetMapping("/user/getRoleNameById")
    ServerResponse<String> getRoleNameById(Long id);
}
