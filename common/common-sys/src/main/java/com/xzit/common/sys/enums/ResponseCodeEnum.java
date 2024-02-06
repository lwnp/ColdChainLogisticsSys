package com.xzit.common.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    DATABASE_ERROR(901,"数据库错误");
    private final Integer code;
    private final String desc;

}
