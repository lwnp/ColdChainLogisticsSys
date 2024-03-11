package com.xzit.logcenter.service;

import com.xzit.common.log.model.vo.ExceptionLogVO;
import com.xzit.common.log.model.vo.OperationLogVO;

public interface LogService {
    Boolean saveOperationLog(OperationLogVO operationLogVO);
    Boolean saveExceptionLog(ExceptionLogVO exceptionLogVO);
}
