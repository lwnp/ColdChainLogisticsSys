package com.xzit.logcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.log.entity.ExceptionLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {
}
