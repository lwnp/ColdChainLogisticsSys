package com.xzit.api.order.feign;

import com.xzit.common.order.entity.Goods;
import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-service",contextId = "goods")
public interface GoodsFeignClient {
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/order/getGoodsById")
    ServerResponse<Goods> getGoodsById(@RequestParam("id") Long id);
}
