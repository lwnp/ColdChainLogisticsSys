package com.xzit.ordercenter.feign;

import com.xzit.api.order.feign.OrderFeignClient;
import com.xzit.common.order.entity.Order;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.ordercenter.mapper.OrderMapper;
import com.xzit.ordercenter.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderFeignController implements OrderFeignClient {
    private final OrderService orderService;

    @Override
    public ServerResponse<Order> getOrderByOrderNum(String orderNum) {
        return ServerResponse.success(orderService.getOrderByOrderNum(orderNum));
    }

    @Override
    public ServerResponse<?> orderFinish(String orderNum) {
        orderService.finishOrder(orderNum);
        return ServerResponse.success();
    }
}
