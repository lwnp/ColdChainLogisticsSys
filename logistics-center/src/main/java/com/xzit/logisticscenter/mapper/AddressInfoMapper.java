package com.xzit.logisticscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.model.dto.AddressInfoDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressInfoMapper extends BaseMapper<AddressInfo> {
    List<AddressInfoDTO> getAddressListByUserId(@Param("userInfoId")Long userInfoId);
    IPage<AddressInfoDTO> getAddressByQuery(IPage<AddressInfoDTO> page, @Param("queryVO")QueryVO queryVO);
    @Select("select count(*) from t_address_info where user_info_id = #{userInfoId}")
    Integer getAddressCountByUserInfoId(@Param("userInfoId")Long userInfoId);
}
