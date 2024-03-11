package com.xzit.api.log.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    String optType() default "";
    public final static String UPDATE="update";
    public final static String INSERT="insert";
    public final static String DELETE="delete";
}
