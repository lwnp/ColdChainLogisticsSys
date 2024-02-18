package com.xzit.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.user.entity.Role;
import com.xzit.common.user.model.dto.RoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT name FROM t_role WHERE id=#{id}")
    String getRoleNameById(Long id);
    @Select("SELECT r.name FROM t_role r LEFT JOIN t_user u ON u.role_id=r.id WHERE u.username=#{username}")
    List<String> listUserRolesName(@Param("username") String username);
}
