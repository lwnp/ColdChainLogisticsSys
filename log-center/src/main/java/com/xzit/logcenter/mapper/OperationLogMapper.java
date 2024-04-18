package com.xzit.logcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.log.entity.OperationLog;
import com.xzit.common.log.model.dto.OperationLogDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    IPage<OperationLogDTO> getOperationLogByQuery(Page<OperationLogDTO> page,@Param("queryVO") QueryVO queryVO);
}
