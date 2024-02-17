package com.xzit.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.user.entity.RoleResource;
import com.xzit.common.user.model.dto.ResourceRoleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceRoleMapper extends BaseMapper<RoleResource> {
    List<ResourceRoleDTO> listResourceRoles();
}
