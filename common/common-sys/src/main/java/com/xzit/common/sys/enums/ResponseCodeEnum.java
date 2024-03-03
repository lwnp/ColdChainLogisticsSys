package com.xzit.common.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    SUCCESS(200,"操作成功"),
    DATABASE_ERROR(901,"数据库错误"),
    CAPTCHA_ERROR(701,"验证码错误"),
    UNAUTHORIZED_ERROR(401,"权限错误");
    private final Integer code;
    private final String desc;

}
