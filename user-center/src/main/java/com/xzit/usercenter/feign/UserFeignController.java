package com.xzit.usercenter.feign;

import com.xzit.api.user.feign.UserFeignClient;

import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import com.xzit.usercenter.mapper.UserMapper;
import com.xzit.usercenter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserFeignController implements UserFeignClient {
    UserService userService;
    @Override
    public ServerResponse<UserDetailsDTO> getUserDetailsByUsername(String username) {
        return ServerResponse.success(userService.getUserByUsername(username));
    }
}
