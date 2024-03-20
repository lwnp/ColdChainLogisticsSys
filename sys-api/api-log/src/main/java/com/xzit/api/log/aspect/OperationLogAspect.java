package com.xzit.api.log.aspect;


import com.alibaba.fastjson2.JSON;
import com.xzit.api.log.event.OperationLogEvent;
import com.xzit.common.log.entity.OperationLog;
import com.xzit.common.sys.utils.IpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import com.xzit.api.log.annotation.OptLog;
import org.springframework.security.oauth2.jwt.Jwt;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.xzit.api.log.annotation.OptLog)")
    public void operationLogPointCut() {
    }

    @AfterReturning(value = "operationLogPointCut()", returning = "keys")
    @SuppressWarnings("unchecked")
    public void saveOperationLog(JoinPoint joinPoint, Object keys) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        OperationLog operationLog = new OperationLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Tags api = (Tags) signature.getDeclaringType().getAnnotation(Tags.class);
        Operation apiOperation = method.getAnnotation(Operation.class);
        OptLog optLog = method.getAnnotation(OptLog.class);
        operationLog.setOptModule(api.value()[0].name());
        operationLog.setOptType(optLog.optType());
        operationLog.setOptDesc(apiOperation.description());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        methodName = className + "." + methodName;
        operationLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        operationLog.setOptMethod(methodName);
        if (joinPoint.getArgs().length > 0) {
            if (joinPoint.getArgs()[0] instanceof MultipartFile) {
                operationLog.setRequestParam("file");
            } else {
                operationLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
            }
        }
        if(SecurityContextHolder.getContext()!=null){
            Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Map<String,Object> map=jwt.getClaims();
            Long userId= (Long) map.get("userId");
            operationLog.setUserId(userId);
        }
        operationLog.setResponseData(JSON.toJSONString(keys));
        String ipAddress = IpUtil.getIpAddress(request);
        operationLog.setIpAddress(ipAddress);
        operationLog.setIpSource(IpUtil.getIpSource(ipAddress));
        operationLog.setOptUri(request.getRequestURI());
        applicationContext.publishEvent(new OperationLogEvent(operationLog));
    }

}
