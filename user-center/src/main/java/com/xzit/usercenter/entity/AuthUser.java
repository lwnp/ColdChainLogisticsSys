package com.xzit.usercenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_user")
public class AuthUser {
    @TableId(type=IdType.AUTO,value = "id")
    Long id;
    @TableField("user_info_id")
    Long userInfoId;
    @TableField("role_id")
    Long roleId;
    String username;
    String password;
    @TableField("is_disable")
    Boolean isDisable;
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    LocalDateTime updateTime;
    @TableField(value = "last_login_time")
    LocalDateTime lastLoginTime;
}
