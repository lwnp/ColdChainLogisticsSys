package com.xzit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.xzit.api.logistics.feign.CourierFeignClient;
import com.xzit.common.sys.constant.UserConstant;
import com.xzit.common.sys.exception.BizException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserInfoMapper userInfoMapper;
    private final CaptchaService captchaService;
    private final CourierFeignClient courierFeignClient;
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
    @Transactional(rollbackFor = Exception.class)
    public void register(UserVO user) {
        if (!captchaService.checkCaptcha(user.getEmail(),user.getCode())){
            throw new BizException("验证码错误");
        }
        UserInfo userInfo=UserInfo.builder()
                .email(user.getEmail())
                .nickname(UserConstant.DEFAULT_NICKNAME)
                .avatar(UserConstant.DEFAULT_AVATAR_URL)
                .build();
        userInfoMapper.insert(userInfo);
        AuthUser authUser= AuthUser.builder()
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .roleId(UserConstant.DEFAULT_ROLE_NUM)
                .build();
        userMapper.insert(authUser);
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

    @Override
    public Boolean addAdminAccount(String username) {
        AuthUser user= isValidUser(username);
        if(user==null){
            return false;
        }
        user.setRoleId(2L);
        return userMapper.updateById(user)==1;
    }

    @Override
    public Boolean addCourierAccount(String username) {
        AuthUser user= isValidUser(username);
        if(user==null){
            return false;
        }
        user.setRoleId(3L);
        return userMapper.updateById(user)==1;
    }

    @Override
    public Boolean resetUserAccount(String username) {
        AuthUser user=resetCheck(username);
        if (user==null||courierFeignClient.isBindCourier(user.getUserInfoId()).getData()){
            return false;
        }
        user.setRoleId(4L);
        return userMapper.updateById(user)==1;
    }


    private AuthUser isValidUser(String username){
        if (StringUtils.isAnyBlank(username)){
            return null;
        }
        AuthUser user=new LambdaQueryChainWrapper<>(userMapper).eq(AuthUser::getUsername,username).one();
        if (user==null||!user.getRoleId().equals(4L)||user.getIsDisable().equals(true)){
            return null;
        }
        return user;
    }
    private AuthUser resetCheck(String username){
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        AuthUser authUser=userMapper.selectById(userId);
        if (StringUtils.isAnyBlank(username)){
            return null;
        }
        AuthUser user=new LambdaQueryChainWrapper<>(userMapper).eq(AuthUser::getUsername,username).one();
        if (user==null||user.getIsDisable().equals(true)){
            return null;
        }
        if(Objects.equals(authUser.getUsername(), username)){
            return null;
        }
        if(authUser.getRoleId().equals(2L)){
            if(user.getRoleId().equals(3L)||user.getRoleId().equals(4L)){
                return user;
            }
            else return null;
        }
        return user;
    }

}
