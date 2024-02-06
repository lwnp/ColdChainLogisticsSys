package com.xzit.common.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role_menu")
public class RoleMenu {
    @TableId(type= IdType.AUTO,value = "id")
    Long id;
    @TableField("role_id")
    Long roleId;
    @TableField("menu_id")
    Long menuId;

}
