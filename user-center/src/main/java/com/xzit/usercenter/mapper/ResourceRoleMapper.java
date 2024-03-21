package com.xzit.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.user.entity.RoleResource;
import com.xzit.common.user.model.dto.ResourceRoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.access.method.P;

import java.util.List;

@Mapper
public interface ResourceRoleMapper extends BaseMapper<RoleResource> {
    List<ResourceRoleDTO> listResourceRoles();
    @Select("select * from t_role_resource where role_id=#{roleId} and resource_id=#{resourceId}")
    RoleResource getByRoleIdAndResourceId(@Param("roleId") Long roleId, @Param("resourceId") Long resourceId);
}
