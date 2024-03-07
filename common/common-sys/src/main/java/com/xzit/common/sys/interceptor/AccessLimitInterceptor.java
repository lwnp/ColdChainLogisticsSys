package com.xzit.common.sys.interceptor;

import com.alibaba.fastjson2.JSON;
import com.xzit.common.sys.annotation.AccessLimit;
import com.xzit.common.sys.constant.ResponseConstant;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.sys.utils.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


@Component
@SuppressWarnings("all")
@Slf4j
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Autowired
    @Qualifier("myRedis")
    RedisTemplate<String,Object> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit != null) {
                long seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();
                String key = IpUtil.getIpAddress(request) + "-" + handlerMethod.getMethod().getName();
                try {
                    long q = incrExpire(key, seconds);
                    if (q > maxCount) {
                        render(response, ServerResponse.fail(ResponseCodeEnum.FORBIDDEN));
                        log.warn(key + "请求次数超过每" + seconds + "秒" + maxCount + "次");
                        return false;
                    }
                    return true;
                } catch (RedisConnectionFailureException e) {
                    log.warn("redis错误: " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }
    private void render(HttpServletResponse response, ServerResponse<?> resultVO) throws Exception {
        response.setContentType(ResponseConstant.CONTENT_TYPE_JSON);
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(resultVO);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
    private Long incrExpire(String key, long time) {
        Long count = redisTemplate.opsForValue().increment(key, 1);
        if (count != null && count == 1) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return count;
    }
}
