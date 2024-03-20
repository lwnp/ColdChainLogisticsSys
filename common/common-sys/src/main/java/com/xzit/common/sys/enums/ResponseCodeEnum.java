package com.xzit.common.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    INVALID_ACCOUNT(444,"账号不符合要求"),
    FORBIDDEN(403,"请稍后再请求"),
    BIND_ERROR(400,"数据绑定错误"),
    SUCCESS(200,"操作成功"),
    FAIL(801,"操作失败"),
    DATABASE_ERROR(901,"数据库错误"),
    CAPTCHA_ERROR(701,"验证码错误"),
    UNAUTHORIZED_ERROR(401,"权限错误");
    private final Integer code;
    private final String desc;

}
