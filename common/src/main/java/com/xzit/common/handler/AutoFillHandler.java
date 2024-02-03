package com.xzit.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 配置mybatis-plus 自动填充日期
 */
@Component
public class AutoFillHandler implements MetaObjectHandler {
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",LocalDateTime.now().format(formatter),metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",LocalDateTime.now().format(formatter),metaObject);
    }
}
