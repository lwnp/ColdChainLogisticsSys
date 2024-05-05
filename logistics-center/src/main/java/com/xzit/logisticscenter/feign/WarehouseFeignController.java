package com.xzit.logisticscenter.feign;

import com.xzit.api.logistics.feign.WarehouseFeignClient;
import com.xzit.common.logistics.entity.LimitTemp;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.logisticscenter.service.LimitTempService;
import com.xzit.logisticscenter.service.LogisticFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WarehouseFeignController implements WarehouseFeignClient {
    private final LogisticFlowService logisticFlowService;
    private final LimitTempService limitTempService;

    @Override
    public ServerResponse<Long> getCenterIdByOrderNum(String orderNum) {
        return ServerResponse.success(logisticFlowService.getCenterIdByOrderNum(orderNum));
    }

    @Override
    public ServerResponse<LimitTemp> getLimitTempById(Long id) {
        return ServerResponse.success(limitTempService.getLimitTempById(id));
    }

    @Override
    public ServerResponse<List<LimitTemp>> getLimitTempList() {
        return ServerResponse.success(limitTempService.getLimitTempList());
    }
}
