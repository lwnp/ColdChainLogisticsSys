package com.xzit.logisticscenter.feign;

import com.xzit.api.logistics.feign.AddressInfoFeignClient;
import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.model.dto.AddressInfoDTO;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.logisticscenter.service.AddressInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressInfoFeignController implements AddressInfoFeignClient {
    private final AddressInfoService  addressInfoService;
    @Override
    public ServerResponse<AddressInfoDTO> getAddressInfoDTO(Long addressId) {
        return ServerResponse.success(addressInfoService.getAddressInfoDTOById(addressId));
    }

    @Override
    public ServerResponse<AddressInfo> getAddressInfo(Long addressId) {
        return ServerResponse.success(addressInfoService.getAddressInfoById(addressId));
    }
}
