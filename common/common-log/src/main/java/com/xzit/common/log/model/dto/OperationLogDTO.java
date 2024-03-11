package com.xzit.common.log.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationLogDTO {

    private Long id;

    private String optModule;

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

    private LocalDateTime createTime;
}
