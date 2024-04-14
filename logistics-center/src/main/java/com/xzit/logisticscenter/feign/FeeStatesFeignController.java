package com.xzit.logisticscenter.feign;

import com.xzit.api.logistics.feign.FeeStatesFeignClient;
import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeeStatesFeignController implements FeeStatesFeignClient {
    @Override
    public ServerResponse<List<FeeStates>> getAllFeeStates() {
        return null;
    }
}
