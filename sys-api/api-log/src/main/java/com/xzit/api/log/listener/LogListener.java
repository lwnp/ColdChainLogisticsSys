package com.xzit.api.log.listener;

import com.xzit.api.log.event.ExceptionLogEvent;
import com.xzit.api.log.event.OperationLogEvent;
import com.xzit.api.log.feign.LogFeignClient;
import com.xzit.common.log.model.vo.ExceptionLogVO;
import com.xzit.common.log.model.vo.OperationLogVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogListener {
    private final LogFeignClient logFeignClient;

    @Async
    @EventListener(OperationLogEvent.class)
    public void saveOperationLog(OperationLogEvent operationLogEvent){
        OperationLogVO operationLogVO= BeanCopyUtil.copyObject(operationLogEvent.getSource(),OperationLogVO.class);
        logFeignClient.saveOperationLog(operationLogVO);
    }
    @Async
    @EventListener(ExceptionLogEvent.class)
    public void saveExceptionLog(ExceptionLogEvent exceptionLogEvent){
        ExceptionLogVO exceptionLogVO=BeanCopyUtil.copyObject(exceptionLogEvent.getSource(), ExceptionLogVO.class);
        logFeignClient.saveExceptionLog(exceptionLogVO);
    }

}
