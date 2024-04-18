package com.xzit.logisticscenter.feign;

import com.xzit.api.logistics.feign.ArrangementFeignClient;
import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.model.dto.ArrangeDistanceDTO;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.logisticscenter.service.LogisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArrangementFeignController implements ArrangementFeignClient {
    private final LogisticService logisticService;
    @Override
    public ServerResponse<ArrangeDistanceDTO> getArrangementList(Long fromAddress, Long toAddress, Long goods) {
        return ServerResponse.success(logisticService.getArrangeDistance(fromAddress,toAddress,goods));
    }

    @Override
    public ServerResponse<BigDecimal> arrangeOrder(String orderNum) {
        return ServerResponse.success(logisticService.arrangeOrder(orderNum));
    }

    @Override
    public ServerResponse<?> recoverArrange(String orderNum) {
        logisticService.recoverArrange(orderNum);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse<Long> getArrangementIdByCarId(Long carId) {
        return ServerResponse.success(logisticService.getArrangementIdByCarId(carId));
    }


}
