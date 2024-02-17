package com.xzit.usercenter.service.impl;

import com.xzit.common.user.model.dto.ResourceRoleDTO;
import com.xzit.usercenter.mapper.ResourceRoleMapper;
import com.xzit.usercenter.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    ResourceRoleMapper resourceRoleMapper;
    @Override
    public List<ResourceRoleDTO> listResourceRoles() {
        return resourceRoleMapper.listResourceRoles();
    }
}
