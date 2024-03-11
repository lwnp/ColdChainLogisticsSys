package com.xzit.common.log.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_operation_log")
public class OperationLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String optModule;
    @TableField(value ="opt_url")
    private String optUri;

    private String optType;

    private String optMethod;

    private String optDesc;

    private String requestMethod;

    private String requestParam;

    private String responseData;

    private Long userId;

    private String ipAddress;

    private String ipSource;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
