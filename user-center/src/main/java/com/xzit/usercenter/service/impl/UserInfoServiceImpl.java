package com.xzit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.user.entity.AuthUser;
import com.xzit.common.user.entity.UserInfo;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.usercenter.mapper.RoleMapper;
import com.xzit.usercenter.mapper.UserInfoMapper;
import com.xzit.usercenter.mapper.UserMapper;
import com.xzit.usercenter.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoMapper userInfoMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
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
        return userInfoDTO;
    }
}
