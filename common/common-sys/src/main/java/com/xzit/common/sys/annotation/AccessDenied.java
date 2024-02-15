package com.xzit.common.sys.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessDenied {
    int seconds();

    int maxCount();
}
