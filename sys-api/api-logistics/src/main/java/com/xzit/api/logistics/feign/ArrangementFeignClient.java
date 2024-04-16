package com.xzit.api.logistics.feign;

import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.model.dto.ArrangeDistanceDTO;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(value = "logistics-service",contextId = "arrangement")
public interface ArrangementFeignClient {
    @PostMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/getArrangeDistanceDTO")
    ServerResponse<ArrangeDistanceDTO>  getArrangementList(@RequestParam(value = "fromAddressInfoId") Long fromAddressInfoId, @RequestParam(value = "toAddressInfoId") Long toAddressInfoId, @RequestParam(value = "goodsId") Long goodsId);
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/arrangeOrder")
    ServerResponse<BigDecimal> arrangeOrder(@RequestParam(value = "orderNum")String orderNum);
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/logistics/recoverArrange")
    ServerResponse<?> recoverArrange(@RequestParam(value = "orderNum")String orderNum);
}
