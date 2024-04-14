package com.xzit.ordercenter.feign;

import com.xzit.api.order.feign.GoodsFeignClient;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.ordercenter.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GoodsFeignController implements GoodsFeignClient {
    private final GoodsService goodsService;
    @Override
    public ServerResponse<Goods> getGoodsById(Long id) {
        return ServerResponse.success(goodsService.getGoodsById(id));
    }
}
