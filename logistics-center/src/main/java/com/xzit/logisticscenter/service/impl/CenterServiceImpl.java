package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.logistics.entity.Center;
import com.xzit.common.logistics.model.dto.CenterDTO;
import com.xzit.common.logistics.model.vo.CenterVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.logisticscenter.mapper.CenterMapper;
import com.xzit.logisticscenter.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {
    private final CenterMapper centerMapper;
    @Override
    public Boolean addLogisticsCenter(CenterVO centerVO) {
        if (centerVO == null) {
            return false;
        }

        if (isEmpty(centerVO.getName()) ||
                isEmpty(centerVO.getAddress()) ||
                centerVO.getAreaId() == null ||
                centerVO.getLongitude() == null ||
                centerVO.getLatitude() == null ||
                centerVO.getMaxSpace() == null) {
            return false;
        }
        Center center= BeanCopyUtil.copyObject(centerVO, Center.class);
        center.setFreeSpace(centerVO.getMaxSpace());
        return centerMapper.insert(center)==1;
    }

    @Override
    public IPage<CenterDTO> getActiveCenterByQuery(QueryVO queryVO) {
        Page<CenterDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return centerMapper.getActiveCenterByQuery(page,queryVO);
    }

    @Override
    public Boolean disableCenter(Long id) {
        Center center=centerMapper.selectById(id);
        if (center==null||center.getIsDisable()){
            return false;
        }
        center.setIsDisable(true);
        return centerMapper.updateById(center)==1;
    }

    @Override
    public IPage<CenterDTO> getDisableCenterByQuery(QueryVO queryVO) {
        Page<CenterDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return centerMapper.getDisableCenterByQuery(page,queryVO);
    }

    @Override
    public Boolean activeCenter(Long id) {
        Center center=centerMapper.selectById(id);
        if(center==null||!center.getIsDisable()){
            return false;
        }
        center.setIsDisable(false);
        return centerMapper.updateById(center)==1;
    }

    @Override
    public Boolean modifyCenter(CenterVO centerVO,Long centerId) {


        return null;
    }

    private boolean isEmpty(String str) {
        return str == null || StringUtils.isEmpty(str);
    }
}
