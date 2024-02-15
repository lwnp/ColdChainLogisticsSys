package com.xzit.usercenter.service.impl;

import com.xzit.usercenter.mapper.RoleMapper;
import com.xzit.usercenter.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    RoleMapper roleMapper;
    @Override
    public String getRoleNameById(Long id) {
        return roleMapper.getRoleNameById(id);
    }
}
