package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.model.dto.AddressInfoDTO;
import com.xzit.common.logistics.model.vo.AddressInfoVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.logisticscenter.mapper.AddressInfoMapper;

import com.xzit.logisticscenter.service.AddressInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressInfoServiceImpl implements AddressInfoService {
    private final AddressInfoMapper addressInfoMapper;

    @Override
    public List<AddressInfoDTO> getAddressInfoList() {

        return null;
    }

    @Override
    public IPage<AddressInfoDTO> getUserAddressInfoByQuery(QueryVO queryVO) {
        return null;
    }

    @Override
    public Boolean deleteAddressInfo(Long userInfoId, Long addressInfoId) {
        return null;
    }

    @Override
    public Boolean modifyAddressInfo(AddressInfoVO addressInfoVO, Long addressInfoId) {
        return null;
    }


}
