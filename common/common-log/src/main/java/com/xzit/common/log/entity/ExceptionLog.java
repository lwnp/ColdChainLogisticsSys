package com.xzit.common.log.entity;

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
@TableName("t_exception_log")
public class ExceptionLog {

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(value = "opt_url")
    private String optUri;
    @TableField(value = "opt_method")
    private String optMethod;
    @TableField(value = "request_method")
    private String requestMethod;
    @TableField(value = "request_param")
    private String requestParam;
    @TableField(value = "opt_desc")
    private String optDesc;
    @TableField(value = "exception_info")
    private String exceptionInfo;
    @TableField(value = "ip_address")
    private String ipAddress;
    @TableField(value = "ip_source")
    private String ipSource;
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private LocalDateTime createTime;

}
