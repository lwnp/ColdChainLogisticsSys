package com.xzit.usercenter.feign;

import com.xzit.api.user.feign.UserFeignClient;

import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.user.model.dto.UserDetailsDTO;
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

    @Override
    public ServerResponse<?> userAuthenticationSuccess(Long userId) {
        if(userService.updateUserLoginTime(userId)){
            return ServerResponse.success();
        }
        else return ServerResponse.fail(ResponseCodeEnum.DATABASE_ERROR.getCode(),ResponseCodeEnum.DATABASE_ERROR.getDesc());
    }
}
