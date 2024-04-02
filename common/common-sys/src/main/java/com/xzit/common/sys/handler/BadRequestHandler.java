package com.xzit.common.sys.handler;

import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.sys.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class BadRequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(BadRequestHandler.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ServerResponse<?> validationBodyException(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        logger.error("Validation Error: {}", errors);
        return ServerResponse.fail(ResponseCodeEnum.BIND_ERROR,errors);
    }
    @ExceptionHandler(BizException.class)
    public ServerResponse<?> bizException(BizException exception){
        logger.error("Biz Error: {}", exception.getMessage());
        return ServerResponse.fail(ResponseCodeEnum.FAIL,exception.getMessage());
    }

}
