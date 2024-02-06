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
@TableName("t_resource")
public class Resource {
    @TableId(type= IdType.AUTO,value = "id")
    Long id;
    String name;
    String url;
    @TableField("request_method")
    String requestMethod;
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    LocalDateTime updateTime;
}
