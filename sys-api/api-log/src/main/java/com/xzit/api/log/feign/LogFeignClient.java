package com.xzit.api.log.feign;

import com.xzit.common.log.model.dto.OperationLogDTO;
import com.xzit.common.log.model.vo.ExceptionLogVO;
import com.xzit.common.log.model.vo.OperationLogVO;
import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "log-service",contextId ="log")
public interface LogFeignClient {
    @PostMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/log/operationLog")
    ServerResponse<?> saveOperationLog(@RequestBody OperationLogVO operationLogVO);
    @PostMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/log/exceptionLog")
    ServerResponse<?> saveExceptionLog(@RequestBody ExceptionLogVO exceptionLogVO);
}
