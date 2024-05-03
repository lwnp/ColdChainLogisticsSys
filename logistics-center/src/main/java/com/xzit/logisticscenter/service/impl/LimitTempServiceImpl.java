package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.logistics.entity.Center;
import com.xzit.common.logistics.entity.LimitTemp;
import com.xzit.common.logistics.model.dto.LimitTempDTO;
import com.xzit.common.logistics.model.vo.LimitTempVO;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.logisticscenter.mapper.CenterMapper;
import com.xzit.logisticscenter.mapper.LimitTempMapper;
import com.xzit.logisticscenter.repository.LimitTempRepository;
import com.xzit.logisticscenter.service.LimitTempService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LimitTempServiceImpl implements LimitTempService {
    private final LimitTempMapper limitTempMapper;
    private final LimitTempRepository limitTempRepository;
    @Override
    public IPage<LimitTempDTO> getLimitTempByQuery(QueryVO queryVO) {
        Page<LimitTempDTO> page = new Page<>(queryVO.getPageNum(),queryVO.getPageSize());
        return limitTempMapper.getLimitTempByQuery(page,queryVO);
    }

    @Override
    public void modifyLimitTemp(LimitTempVO limitTempVO) {
        if(limitTempVO.getMin()>=limitTempVO.getMax()){
            throw new BizException("最低温度不能大于等于最高温");
        }
        LimitTemp limitTemp=limitTempMapper.selectById(limitTempVO.getId());
        if (limitTemp!=null){
            limitTemp.setMin(limitTempVO.getMin());
            limitTemp.setMax(limitTempVO.getMax());
            limitTemp.setUpdateTime(new Date());
            limitTempMapper.updateById(limitTemp);
            limitTempRepository.save(limitTemp);
        }
        else {
            throw new BizException("该id不存在");
        }
    }

    @Override
    public LimitTemp getLimitTempById(Long id) {
        return limitTempRepository.findByCenterId(id);
    }
}
