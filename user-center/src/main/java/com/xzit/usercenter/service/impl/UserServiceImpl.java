package com.xzit.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.user.entity.AuthUser;
import com.xzit.common.user.entity.Role;
import com.xzit.usercenter.mapper.RoleMapper;
import com.xzit.usercenter.mapper.UserMapper;
import com.xzit.common.user.model.dto.UserDetailsDTO;
import com.xzit.usercenter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserMapper userMapper;
    RoleMapper roleMapper;
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
}
