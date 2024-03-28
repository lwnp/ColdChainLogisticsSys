package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.logistics.constant.LogisticConstant;
import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.model.dto.AddressInfoDTO;
import com.xzit.common.logistics.model.vo.AddressInfoVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.logisticscenter.mapper.AddressInfoMapper;

import com.xzit.logisticscenter.service.AddressInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressInfoServiceImpl implements AddressInfoService {
    private final AddressInfoMapper addressInfoMapper;
    private final UserFeignClient userFeignClient;

    @Override
    public List<AddressInfoDTO> getAddressInfoList() {
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        UserInfoDTO userInfoDTO=userFeignClient.getUserInfo(userId).getData();
        return addressInfoMapper.getAddressListByUserId(userInfoDTO.getId());
    }

    @Override
    public IPage<AddressInfoDTO> getUserAddressInfoByQuery(QueryVO queryVO) {
        Page<AddressInfoDTO> page=new Page<>(queryVO.getPageNum(),queryVO.getPageSize());
        return addressInfoMapper.getAddressByQuery(page,queryVO);
    }

    @Override
    public Boolean deleteAddressInfo(Long addressInfoId) {
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        QueryWrapper<AddressInfo>  queryWrapper=new QueryWrapper<>();
        UserInfoDTO userInfoDTO=userFeignClient.getUserInfo(userId).getData();
        queryWrapper.eq("id",addressInfoId).eq("user_info_id",userInfoDTO.getId());
        if(addressInfoMapper.exists(queryWrapper)){
            return addressInfoMapper.deleteById(addressInfoId)==1;
        }
        return false;
    }

    @Override
    public Boolean modifyAddressInfo(AddressInfoVO addressInfoVO, Long addressInfoId) {
        AddressInfo addressInfo=isValid(addressInfoVO,addressInfoId);
        return addressInfoMapper.updateById(addressInfo)==1;
    }

    @Override
    public Boolean addAddressInfo(AddressInfoVO addressInfoVO) {
        if(addressInfoMapper.getAddressCountByUserInfoId(addressInfoVO.getUserInfoId())> LogisticConstant.MAX_ADDRESS) {
            return false;
        }
        AddressInfo addressInfo=BeanCopyUtil.copyObject(addressInfoVO,AddressInfo.class);
        return addressInfoMapper.insert(addressInfo)==1;

    }

    private AddressInfo isValid(AddressInfoVO addressInfoVO,Long addressInfoId){
        AddressInfo addressInfo=addressInfoMapper.selectById(addressInfoId);
        if(addressInfo==null){
            return null;
        }
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        QueryWrapper<AddressInfo>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",addressInfoId);
        UserInfoDTO userInfoDTO=userFeignClient.getUserInfo(userId).getData();
        if(Objects.equals(userInfoDTO.getId(), addressInfoVO.getUserInfoId())||addressInfoVO.getUserInfoId()==null){
            AddressInfo addressInfoData= BeanCopyUtil.copyObject(addressInfoVO,AddressInfo.class);
            addressInfoData.setId(addressInfoId);
            return addressInfoData;
        }

        return addressInfo;
    }



}
