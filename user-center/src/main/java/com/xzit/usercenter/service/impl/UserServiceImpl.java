package com.xzit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.user.entity.AuthUser;
import com.xzit.usercenter.mapper.UserMapper;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import com.xzit.usercenter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserMapper userMapper;
    @Override
    public UserDetailsDTO getUserByUsername(String username) {
        AuthUser user=new LambdaQueryChainWrapper<>(userMapper).eq(AuthUser::getUsername,username).one();
        return  user==null?null:BeanCopyUtil.copyObject(user, UserDetailsDTO.class);
    }

    @Override
    public Boolean updateUserLoginTime(Long userId) {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String time=LocalDateTime.now().format(formatter);
        return new LambdaUpdateChainWrapper<>(userMapper).eq(AuthUser::getId,userId).set(AuthUser::getLastLoginTime,time).update();
    }
}
