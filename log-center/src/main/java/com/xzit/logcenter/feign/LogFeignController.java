package com.xzit.logcenter.feign;

import com.xzit.api.log.feign.LogFeignClient;
import com.xzit.common.log.model.vo.ExceptionLogVO;
import com.xzit.common.log.model.vo.OperationLogVO;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.logcenter.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogFeignController implements LogFeignClient {
    private final LogService logService;
    @Override
    public ServerResponse<?> saveOperationLog(OperationLogVO operationLogVO) {
        if(logService.saveOperationLog(operationLogVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.DATABASE_ERROR);
    }

    @Override
    public ServerResponse<?> saveExceptionLog(ExceptionLogVO exceptionLogVO) {
        if(logService.saveExceptionLog(exceptionLogVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.DATABASE_ERROR);
    }
}
