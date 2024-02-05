package com.xzit.api.user.feign;


import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "user-service",contextId = "user")
public interface UserFeignClient {
    @GetMapping("/userDetails/getUserDetailsByUsername")
    ServerResponse<UserDetailsDTO> getUserDetailsByUsername(String username);

}
