package com.xzit.api.user.feign;


import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service",contextId = "user-info")
public interface UserFeignClient {
    @GetMapping("/user/getUserDetailsByUsername")
    ServerResponse<UserDetailsDTO> getUserDetailsByUsername(@RequestParam("username") String username);
    @GetMapping("/user/userAuthenticationSuccess")
    ServerResponse<?> userAuthenticationSuccess(Long userId);

}
