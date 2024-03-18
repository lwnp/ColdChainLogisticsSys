package com.xzit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.xzit.common.sys.constant.UserConstant;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.user.entity.AuthUser;
import com.xzit.common.user.entity.Role;
import com.xzit.common.user.entity.UserInfo;
import com.xzit.common.user.model.vo.PasswordVO;
import com.xzit.common.user.model.vo.UserVO;
import com.xzit.usercenter.mapper.RoleMapper;
import com.xzit.usercenter.mapper.UserInfoMapper;
import com.xzit.usercenter.mapper.UserMapper;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import com.xzit.usercenter.service.CaptchaService;
import com.xzit.usercenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserInfoMapper userInfoMapper;
    private final CaptchaService captchaService;
    @Override
    public UserDetailsDTO getUserByUsername(String username) {
        AuthUser user=new LambdaQueryChainWrapper<>(userMapper).eq(AuthUser::getUsername,username).one();
        if(user==null){
            return null;
        }
        UserDetailsDTO userDetailsDTO=BeanCopyUtil.copyObject(user, UserDetailsDTO.class);
        Role role=roleMapper.selectById(user.getRoleId());
        List<String> roleList=new ArrayList<>();
        roleList.add(role.getName());
        userDetailsDTO.setRoles(roleList);
        return userDetailsDTO;
    }

    @Override
    public Boolean updateUserLoginTime(Long userId) {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String time=LocalDateTime.now().format(formatter);
        return new LambdaUpdateChainWrapper<>(userMapper).eq(AuthUser::getId,userId).set(AuthUser::getLastLoginTime,time).update();
    }

    @Override
    public Boolean hasSameUser(String username) {
        AuthUser user=new LambdaQueryChainWrapper<>(userMapper).eq(AuthUser::getUsername,username).one();
        return user!=null;
    }

    @Override
    public Boolean register(UserVO user) {
        if (!captchaService.checkCaptcha(user.getEmail(),user.getCode())){
            return false;
        }
        UserInfo userInfo=UserInfo.builder()
                .email(user.getEmail())
                .nickname(UserConstant.DEFAULT_NICKNAME)
                .avatar(UserConstant.DEFAULT_AVATAR_URL)
                .build();
        if(userInfoMapper.insertUserInfo(userInfo)!=1){
            return false;
        }
        AuthUser authUser= AuthUser.builder()
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .roleId(UserConstant.DEFAULT_ROLE_NUM)
                .build();
        int flag=userMapper.insert(authUser);
        return flag==1;
    }

    @Override
    public Boolean forbidden(Long userInfoId) {
        return userMapper.forbiddenByUserInfoId(userInfoId)==1;
    }

    @Override
    public Boolean resetPassword(PasswordVO passwordVO) {
        if (StringUtils.isAnyBlank(passwordVO.getUsername(),passwordVO.getPassword(),passwordVO.getCode())){
            return false;
        }
        AuthUser user=new LambdaQueryChainWrapper<>(userMapper).eq(AuthUser::getUsername,passwordVO.getUsername()).one();
        String email=userInfoMapper.getEmailByUsername(user.getUsername());
        if(!captchaService.checkCaptcha(email, passwordVO.getCode())){
            return false;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(passwordVO.getPassword()));
        return userMapper.updateById(user)==1;
    }

}
