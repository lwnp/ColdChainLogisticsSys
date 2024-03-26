package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
    public IPage<CenterDTO> getCenterByQuery(QueryVO queryVO) {
        return null;
    }

    private boolean isEmpty(String str) {
        return str == null || StringUtils.isEmpty(str);
    }
}
