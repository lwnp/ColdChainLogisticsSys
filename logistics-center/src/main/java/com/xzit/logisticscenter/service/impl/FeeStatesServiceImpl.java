package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.common.logistics.model.dto.FeeStatesDTO;
import com.xzit.common.logistics.model.vo.FeeStatesVO;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.logisticscenter.mapper.FeeStatesMapper;
import com.xzit.logisticscenter.service.FeeStatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeeStatesServiceImpl implements FeeStatesService {
    private final FeeStatesMapper feeStatesMapper;
    @Override
    public List<FeeStatesDTO> getFeeStates() {
        return feeStatesMapper.getFeeStates();
    }

    @Override
    public void modifyFeeStates(FeeStatesVO feeStatesVO) {
        FeeStates feeStates=updateCheck().apply(feeStatesVO);
        QueryWrapper<FeeStates> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fee_type_id",feeStatesVO.getFeeTypeId());
        List<FeeStates> feeStatesList = feeStatesMapper.selectList(queryWrapper).stream()
                .filter(fee -> !Objects.equals(fee.getId(), feeStatesVO.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
        feeStatesList.add(feeStates);
        feeStatesList.sort(Comparator.comparingDouble(FeeStates::getLimit));
        long flag=1;
        for (FeeStates fee:feeStatesList){
            fee.setState(flag);
            feeStatesMapper.updateById(fee);
            flag++;
        }
    }

    @Override
    public void addFeeStates(FeeStatesVO feeStatesVO) {
        FeeStates states=addCheck().apply(feeStatesVO);
        QueryWrapper<FeeStates> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fee_type_id",states.getFeeTypeId());
        List<FeeStates> feeStatesList = feeStatesMapper.selectList(queryWrapper);
        feeStatesList.add(states);
        feeStatesList.sort(Comparator.comparingDouble(FeeStates::getLimit));
        long flag=1;
        for (FeeStates fee:feeStatesList){
            fee.setState(flag);
            if(fee.getId()==null){
                feeStatesMapper.insert(fee);
            }
            else {
                feeStatesMapper.updateById(fee);
            }
            flag++;
        }

    }
    private Function<FeeStatesVO,FeeStates> updateCheck(){
        return feeStatesVO -> {
            if(feeStatesVO.getId()==null){
                throw new BizException("id can not be null");
            }
            FeeStates feeStates=feeStatesMapper.selectById(feeStatesVO.getId());
            if(feeStates==null){
                throw new BizException("id is not exist");
            }
            if(feeStatesVO.getLimit()!=null){
                feeStates.setLimit(feeStatesVO.getLimit());
            }
            if(feeStatesVO.getOilRate()!=null){
                feeStates.setOilRate(feeStatesVO.getOilRate());
            }
            if(feeStatesVO.getPrice()!=null){
                feeStates.setPrice(feeStatesVO.getPrice());
            }
            return feeStates;
        };
    }
    private Function<FeeStatesVO,FeeStates> addCheck(){
        return feeStatesVO -> {
            if (feeStatesVO.getFeeTypeId()==null||feeStatesVO.getPrice()==null||feeStatesVO.getLimit()==null||feeStatesVO.getOilRate()==null){
                throw new BizException("param can not be null");
            }
            if(feeStatesVO.getFeeTypeId()==1){
                throw new BizException("can not add fee type 1");
            }
            return BeanCopyUtil.copyObject(feeStatesVO,FeeStates.class);
        };
    }
}
