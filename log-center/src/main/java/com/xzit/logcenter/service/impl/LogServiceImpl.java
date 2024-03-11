package com.xzit.logcenter.service.impl;

import com.xzit.common.log.entity.ExceptionLog;
import com.xzit.common.log.entity.OperationLog;
import com.xzit.common.log.model.vo.ExceptionLogVO;
import com.xzit.common.log.model.vo.OperationLogVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.logcenter.mapper.ExceptionLogMapper;
import com.xzit.logcenter.mapper.OperationLogMapper;
import com.xzit.logcenter.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final OperationLogMapper operationLogMapper;
    private final ExceptionLogMapper exceptionLogMapper;
    @Override
    public Boolean saveOperationLog(OperationLogVO operationLogVO) {
        OperationLog operationLog= BeanCopyUtil.copyObject(operationLogVO, OperationLog.class);
        return operationLogMapper.insert(operationLog)==1;
    }

    @Override
    public Boolean saveExceptionLog(ExceptionLogVO exceptionLogVO) {
        ExceptionLog exceptionLog=BeanCopyUtil.copyObject(exceptionLogVO, ExceptionLog.class);
        return exceptionLogMapper.insert(exceptionLog)==1;
    }
}
