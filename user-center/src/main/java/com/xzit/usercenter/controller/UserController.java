package com.xzit.usercenter.controller;

import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.user.model.vo.UserVO;
import com.xzit.usercenter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "用户模块")
public class UserController {
    @Autowired
    UserService userService;
    @Operation(summary = "查询是否存在同名用户")
    @GetMapping("/hasSameUser")
    ServerResponse<Boolean> hasSameUser(@RequestParam String username){
        return ServerResponse.success(userService.hasSameUser(username));
    }
    @Operation(summary = "注册用户")
    @PostMapping("/register")
    ServerResponse<?> register(@RequestBody @Valid UserVO userVO){
        if(userService.register(userVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.CAPTCHA_ERROR.getCode(),ResponseCodeEnum.CAPTCHA_ERROR.getDesc());
    }

}
