package com.xzit.common.log.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionLogVO {
    private String optUri;

    private String optMethod;

    private String requestMethod;

    private String requestParam;

    private String optDesc;

    private String exceptionInfo;

    private String ipAddress;

    private String ipSource;
}
