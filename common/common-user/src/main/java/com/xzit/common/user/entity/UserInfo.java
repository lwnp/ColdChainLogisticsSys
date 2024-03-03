package com.xzit.common.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_info")
public class UserInfo {
    @TableId(type= IdType.AUTO,value = "id")
    Long id;
    String nickname;
    String avatar;
    String email;
    String phone;
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    LocalDateTime updateTime;
}
