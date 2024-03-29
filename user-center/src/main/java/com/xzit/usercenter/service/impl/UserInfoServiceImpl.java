package com.xzit.usercenter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.xzit.common.sys.constant.UserConstant;
import com.xzit.common.sys.model.vo.EmailVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.user.entity.AuthUser;
import com.xzit.common.user.entity.UserInfo;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.common.user.model.vo.UserInfoVO;
import com.xzit.usercenter.mapper.RoleMapper;
import com.xzit.usercenter.mapper.UserInfoMapper;
import com.xzit.usercenter.mapper.UserMapper;
import com.xzit.usercenter.service.CaptchaService;
import com.xzit.usercenter.service.FileService;
import com.xzit.usercenter.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoMapper userInfoMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final CaptchaService captchaService;
    @Override
    public UserInfoDTO loadUserInfoByContext() {
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        AuthUser authUser=userMapper.selectById(userId);
        UserInfo userInfo=new LambdaQueryChainWrapper<>(userInfoMapper).eq(UserInfo::getId,authUser.getUserInfoId()).one();
        UserInfoDTO userInfoDTO= BeanCopyUtil.copyObject(userInfo, UserInfoDTO.class);
        userInfoDTO.setRoles(roleMapper.listUserRolesName(authUser.getUsername()));
        userInfoDTO.setUsername(authUser.getUsername());
        userInfoDTO.setIsDisable(authUser.getIsDisable());
        return userInfoDTO;
    }

    @Override
    public Boolean updateUserInfo(UserInfoVO userInfoVO,Long userInfoId) {
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        AuthUser authUser=userMapper.selectById(userId);
        if(Objects.equals(authUser.getUserInfoId(), userInfoId)){
            if(userInfoVO.getNickname().isEmpty()){
                userInfoVO.setNickname(null);
            }
            if (userInfoVO.getAvatar().isEmpty()){
                userInfoVO.setAvatar(null);
            }
            if (userInfoVO.getPhone().isEmpty()){
                userInfoVO.setPhone(null);
            }
            UserInfo userInfo=BeanCopyUtil.copyObject(userInfoVO, UserInfo.class);
            userInfo.setId(userInfoId);
            return userInfoMapper.updateById(userInfo)==1;
        }
        return false;
    }

    @Override
    public IPage<UserInfoDTO> loadUserInfo(QueryVO queryVO) {
        Page<UserInfoDTO> userInfoDTOPage=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return userInfoMapper.getUserInfoByQuery(userInfoDTOPage,queryVO);
    }

    @Override
    public Boolean updateEmail(EmailVO emailVO,Long userInfoId) {
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        AuthUser authUser=userMapper.selectById(userId);
        if(Objects.equals(authUser.getUserInfoId(), userInfoId)){
            if(captchaService.checkCaptcha(emailVO.getEmail(), emailVO.getCode())) {
                UserInfo userInfo= UserInfo.builder().email(emailVO.getEmail()).id(userInfoId).build();
                return userInfoMapper.updateById(userInfo)==1;
            }
        }
        return false;
    }

    @Override
    public Boolean forbiddenAvatar(Long userInfoId) {
        UserInfo userInfo=UserInfo.builder().id(userInfoId).avatar(UserConstant.DEFAULT_AVATAR_URL).build();
        return userInfoMapper.updateById(userInfo)==1;
    }

    @Override
    public Boolean forbiddenNickname(Long userInfoId) {
        UserInfo userInfo=UserInfo.builder().id(userInfoId).nickname(UserConstant.DEFAULT_NICKNAME).build();
        return userInfoMapper.updateById(userInfo)==1;
    }

    @Override
    public void sendEmailByUsername(String username) {
        String email=userInfoMapper.getEmailByUsername(username);
        EmailVO emailVO=EmailVO.builder().email(email).build();
        captchaService.sendCaptchaToExchange(emailVO);

    }

    @Override
    public IPage<UserInfoDTO> listAdminInfoByQuery(QueryVO queryVO) {
        Page<UserInfoDTO> userInfoDTOPage=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return userInfoMapper.getAdminInfoByQuery(userInfoDTOPage,queryVO);
    }

    @Override
    public UserInfoDTO getUserInfoByUserId(Long userId) {
        return userInfoMapper.getUserInfoByUserId(userId);
    }

    @Override
    public Boolean isValidCourier(Long userInfoId) {
        AuthUser user=new LambdaQueryChainWrapper<>(userMapper).eq(AuthUser::getUserInfoId,userInfoId).one();
        return user != null && user.getRoleId() == 3;
    }


}
