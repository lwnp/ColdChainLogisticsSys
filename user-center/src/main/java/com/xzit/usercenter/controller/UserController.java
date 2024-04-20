package com.xzit.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.api.log.annotation.OptLog;
import com.xzit.common.file.service.FileService;
import com.xzit.common.sys.annotation.AccessLimit;
import com.xzit.common.sys.constant.UserConstant;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.sys.model.vo.EmailVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.common.user.model.vo.PasswordVO;
import com.xzit.common.user.model.vo.UserInfoVO;
import com.xzit.common.user.model.vo.UserVO;
import com.xzit.usercenter.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tags(@Tag(name="用户相关"))
public class UserController {
    private final UserService userService;
    private final CaptchaService captchaService;
    private final FileService fileService;
    private final UserInfoService userInfoService;

    @Operation(summary = "查询是否存在同名用户")
    @GetMapping("/hasSameUser")
    ServerResponse<Boolean> hasSameUser(@RequestParam String username){
        return ServerResponse.success(userService.hasSameUser(username));
    }
    @Operation(summary = "注册用户")
    @PostMapping("/register")
    @OptLog(optType = OptLog.INSERT)
    ServerResponse<?> register(@RequestBody @Valid UserVO userVO){
        userService.register(userVO);
        return ServerResponse.success(ResponseCodeEnum.SUCCESS);
    }
    @Operation(summary = "发送邮箱验证码")
    @AccessLimit(seconds = 60,maxCount = 1)
    @PostMapping("/sendCaptcha")
    ServerResponse<?> sendCaptcha(@RequestBody @Valid EmailVO emailVO){
        captchaService.sendCaptchaToExchange(emailVO);
        return ServerResponse.success();
    }
    @PostMapping("/fileUpload")
    @Operation(summary = "上传头像")
    ServerResponse<String> UploadFile(@RequestBody MultipartFile file){
        return ServerResponse.success(fileService.uploadFile(file, UserConstant.AVATAR_SEPARATOR));
    }
    @Operation(summary = "基于token获取用户信息")
    @GetMapping("/loadUserInfoByContext")
    ServerResponse<UserInfoDTO> loadUserInfoByContext(){
        return ServerResponse.success(userInfoService.loadUserInfoByContext());
    }
    @PutMapping("/updateUserInfo/{userInfoId}")
    @Operation(summary = "更新用户信息(不包括邮箱)")
    @OptLog(optType = OptLog.UPDATE)
    ServerResponse<Boolean> updateUserInfo(@RequestBody @Valid UserInfoVO userInfoVO,@PathVariable("userInfoId") Long userInfoId){
        if(userInfoService.updateUserInfo(userInfoVO,userInfoId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);

    }
    @PostMapping("/getUserInfoByQuery")
    @Operation(summary = "获取用户信息",description = "query为空则返回全部")
    ServerResponse<IPage<UserInfoDTO>> getUserInfoByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(userInfoService.loadUserInfo(queryVO));
    }
    @PutMapping("/updateUserEmail/{userInfoId}")
    @OptLog(optType = OptLog.UPDATE)
    @Operation(summary = "更新用户邮箱")
    ServerResponse<Boolean> updateUserEmail(@RequestBody @Valid EmailVO emailVO,@PathVariable("userInfoId") Long userInfoId){
        if(userInfoService.updateEmail(emailVO, userInfoId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);

    }
    @GetMapping("/forbiddenUser/{userInfoId}")
    @Operation(summary = "封禁用户")
    @OptLog(optType = OptLog.UPDATE)
    ServerResponse<Boolean> forbiddenUser(@PathVariable Long userInfoId){
        if (userService.forbidden(userInfoId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @GetMapping("/forbiddenAvatar/{userInfoId}")
    @Operation(summary = "对违规头像重置")
    @OptLog(optType = OptLog.UPDATE)
    ServerResponse<Boolean> forbiddenAvatar(@PathVariable Long userInfoId){
        if(userInfoService.forbiddenAvatar(userInfoId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @GetMapping("/forbiddenNickname/{userInfoId}")
    @Operation(summary = "对违规昵称重置")
    @OptLog(optType = OptLog.UPDATE)
    ServerResponse<Boolean> forbiddenNickname(@PathVariable Long userInfoId){
        if(userInfoService.forbiddenNickname(userInfoId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @GetMapping("/sendEmailByUsername/{username}")
    @Operation(summary = "通过用户名发送重置验证码")
    @AccessLimit(seconds = 60,maxCount = 1)
    ServerResponse<Boolean> sendEmailByUsername(@PathVariable String username){
        if (userService.hasSameUser(username)){
            userInfoService.sendEmailByUsername(username);
            return ServerResponse.success();

        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @PutMapping("/resetPassword")
    @Operation(summary = "重置密码")
    @OptLog(optType = OptLog.UPDATE)
    ServerResponse<Boolean> resetPassword(@RequestBody @Valid PasswordVO passwordVO){
        if (userService.resetPassword(passwordVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @GetMapping("/addAdminAccount/{username}")
    @OptLog(optType = OptLog.UPDATE)
    @Operation(summary = "添加管理员")
    ServerResponse<Boolean> addAdminAccount(@PathVariable String username){
        if(userService.addAdminAccount(username)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.INVALID_ACCOUNT);

    }
    @GetMapping("/addCourierAccount/{username}")
    @OptLog(optType = OptLog.UPDATE)
    @Operation(summary = "添加司机")
    ServerResponse<Boolean> addCourierAccount(@PathVariable String username){
        if(userService.addCourierAccount(username)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.INVALID_ACCOUNT);
    }
    @GetMapping("/resetUserAccount/{username}")
    @OptLog(optType = OptLog.UPDATE)
    @Operation(summary = "重置角色为普通用户")
    ServerResponse<Boolean> resetUserAccount(@PathVariable String username){
        if(userService.resetUserAccount(username)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.INVALID_ACCOUNT);
    }
    @PostMapping("/listAdminByQuery")
    @Operation(summary = "分页查询管理员信息")
    ServerResponse<IPage<UserInfoDTO>> listAdminByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(userInfoService.listAdminInfoByQuery(queryVO));
    }

}
