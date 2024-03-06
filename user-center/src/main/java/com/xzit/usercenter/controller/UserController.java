package com.xzit.usercenter.controller;

import com.xzit.common.sys.constant.UserConstant;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.sys.model.vo.EmailVO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.common.user.model.vo.UserInfoVO;
import com.xzit.common.user.model.vo.UserVO;
import com.xzit.usercenter.service.CaptchaService;
import com.xzit.usercenter.service.FileUploadService;
import com.xzit.usercenter.service.UserInfoService;
import com.xzit.usercenter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "用户模块")
public class UserController {
    private final UserService userService;
    private final CaptchaService captchaService;
    private final FileUploadService fileUploadService;
    private final UserInfoService userInfoService;
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
        return ServerResponse.fail(ResponseCodeEnum.CAPTCHA_ERROR);
    }
    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/sendCaptcha")
    ServerResponse<?> sendCaptcha(@RequestBody @Valid EmailVO emailVO){
        captchaService.sendCaptchaToExchange(emailVO);
        return ServerResponse.success();
    }
    @PostMapping("/fileUpload")
    @Operation(summary = "上传头像")
    ServerResponse<String> UploadFile(@RequestBody MultipartFile file){
        return ServerResponse.success(fileUploadService.uploadFile(file, UserConstant.AVATAR_SEPARATOR));
    }
    @Operation(summary = "基于token获取用户信息")
    @GetMapping("/loadUserInfoByContext")
    ServerResponse<UserInfoDTO> loadUserInfoByContext(){
        return ServerResponse.success(userInfoService.loadUserInfoByContext());
    }
    @PutMapping("/updateUserInfo/{userInfoId}")
    @Operation(summary = "更新用户信息(不包括邮箱)")
    ServerResponse<Boolean> updateUserInfo(@RequestBody @Valid UserInfoVO userInfoVO,@PathVariable("userInfoId") Long userInfoId){
        if(userInfoService.updateUserInfo(userInfoVO,userInfoId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);

    }

}
