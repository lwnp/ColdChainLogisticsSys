package com.xzit.logisticscenter.service.impl;

import com.xzit.common.logistics.entity.Area;
import com.xzit.logisticscenter.mapper.AreaMapper;
import com.xzit.logisticscenter.mapper.CenterMapper;
import com.xzit.logisticscenter.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaMapper areaMapperMapper;
    @Override
    public List<Area> getAreaInfo() {
        return areaMapperMapper.selectList(null);
    }
}
