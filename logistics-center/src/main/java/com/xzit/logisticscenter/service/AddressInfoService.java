package com.xzit.logisticscenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.model.dto.AddressInfoDTO;
import com.xzit.common.logistics.model.vo.AddressInfoVO;
import com.xzit.common.sys.model.vo.QueryVO;

import java.util.List;

public interface AddressInfoService {
    List<AddressInfoDTO> getAddressInfoList();
    IPage<AddressInfoDTO> getUserAddressInfoByQuery(QueryVO queryVO);
    Boolean deleteAddressInfo(Long addressInfoId);
    Boolean modifyAddressInfo(AddressInfoVO addressInfoVO, Long addressInfoId);
    Boolean addAddressInfo(AddressInfoVO addressInfoVO);
    AddressInfoDTO getAddressInfoDTOById(Long addressInfoId);
    AddressInfo getAddressInfoById(Long addressInfoId);
}
