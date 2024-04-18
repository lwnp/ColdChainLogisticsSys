package com.xzit.logcenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.log.entity.ExceptionLog;
import com.xzit.common.log.model.dto.ExceptionLogDTO;
import com.xzit.common.log.model.dto.OperationLogDTO;
import com.xzit.common.log.model.vo.ExceptionLogVO;
import com.xzit.common.log.model.vo.OperationLogVO;
import com.xzit.common.sys.model.vo.QueryVO;

public interface LogService {
    Boolean saveOperationLog(OperationLogVO operationLogVO);
    Boolean saveExceptionLog(ExceptionLogVO exceptionLogVO);
    IPage<OperationLogDTO> getOperationLogByQuery(QueryVO queryVO);
    IPage<ExceptionLogDTO> getExceptionLogByQuery(QueryVO queryVO);
    void deleteOperationLog(Long id);
    void deleteExceptionLog(Long id);
}
