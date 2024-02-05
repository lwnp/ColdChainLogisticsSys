package com.xzit.usercenter.feign;

import com.xzit.api.user.feign.UserFeignClient;

import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFeignController implements UserFeignClient {
    @Override
    public ServerResponse<UserDetailsDTO> getUserDetailsByUsername(String username) {
        return null;
    }
}
