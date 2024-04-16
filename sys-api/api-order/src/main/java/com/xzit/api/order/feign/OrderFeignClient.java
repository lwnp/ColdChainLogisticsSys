package com.xzit.api.order.feign;

import com.xzit.common.order.entity.Order;
import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-service",contextId = "order")
public interface OrderFeignClient {
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/order/getOrderByOrderNum")
    ServerResponse<Order> getOrderByOrderNum(@RequestParam(value = "orderNum") String orderNum);
}
