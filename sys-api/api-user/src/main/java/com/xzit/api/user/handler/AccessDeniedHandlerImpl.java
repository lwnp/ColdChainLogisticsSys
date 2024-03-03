package com.xzit.api.user.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.xzit.common.sys.constant.ResponseConstant;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(ResponseConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(ResponseConstant.ENCODE);
        response.getWriter().println(JSON.toJSONString(ServerResponse.fail(ResponseCodeEnum.UNAUTHORIZED_ERROR)));
    }
}
