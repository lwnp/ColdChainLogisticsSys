package com.xzit.logcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.log.entity.ExceptionLog;
import com.xzit.common.log.model.dto.ExceptionLogDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {
    IPage<ExceptionLogDTO> getExceptionLogByQuery(Page<ExceptionLogDTO> page,@Param("queryVO") QueryVO queryVO);
}
