package com.xzit.hardwarecenter.feign;

import com.xzit.api.hardware.feign.WarehouseFeignClient;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.hardware.model.dto.WarehouseDataDTO;
import com.xzit.hardwarecenter.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WarehouseFeignController implements WarehouseFeignClient {
    private final WarehouseMapper warehouseMapper;
    @Override
    public ServerResponse<List<WarehouseDataDTO>> getWarehouseData(String orderNum) {
        return ServerResponse.success(warehouseMapper.getWarehouseData(orderNum));
    }
}
