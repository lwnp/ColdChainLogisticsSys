package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.logistics.constant.AreaConstant;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.entity.Station;
import com.xzit.common.logistics.model.dto.StationDTO;
import com.xzit.common.logistics.model.vo.StationVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.logisticscenter.mapper.AreaMapper;
import com.xzit.logisticscenter.mapper.StationMapper;
import com.xzit.logisticscenter.service.StationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {
    private final StationMapper stationMapper;
    @Override
    public Boolean addLogisticStation(StationVO stationVO) {
        if (stationVO==null){
            return false;
        }
        if (isEmpty(stationVO.getName()) ||
                isEmpty(stationVO.getAddress()) ||
                stationVO.getAreaId() == null ||
                stationVO.getLongitude() == null ||
                stationVO.getLatitude() == null) {
            return false;
        }
        Station station= BeanCopyUtil.copyObject(stationVO, Station.class);
        return stationMapper.insert(station)==1;
    }

    @Override
    public IPage<StationDTO> getActiveStation(QueryVO queryVO) {
        Page<StationDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return stationMapper.getActiveStationByQuery(page,queryVO);
    }

    @Override
    public Boolean disableStation(Long stationId) {
        Station station=stationMapper.selectById(stationId);
        if(station==null||station.getIsDisable()){
            return false;
        }
        station.setIsDisable(true);
        return stationMapper.updateById(station)==1;
    }

    @Override
    public IPage<StationDTO> getDisableStation(QueryVO queryVO) {
        Page<StationDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return stationMapper.getDisableStationByQuery(page,queryVO);
    }

    @Override
    public Boolean activeStation(Long stationId) {
        Station station=stationMapper.selectById(stationId);
        if(station==null||!station.getIsDisable()){
            return false;
        }
        station.setIsDisable(false);
        return stationMapper.updateById(station)==1;
    }

    @Override
    public Boolean modifyStation(StationVO stationVO,Long stationId) {
        Station station=isValid(stationVO,stationId);
        if(station==null){
            return false;
        }

        return stationMapper.updateById(station)==1;
    }

    private boolean isEmpty(String str) {
        return str == null || StringUtils.isEmpty(str);
    }
    private Station isValid(StationVO stationVO,Long stationId){
        Station station=stationMapper.selectById(stationId);
        if(station!=null){
            if(stationVO.getAddress().isEmpty()){
                stationVO.setAddress(null);
            }
            if (stationVO.getName().isEmpty()){
                stationVO.setName(null);
            }
            if (stationVO.getAreaId()<=0||stationVO.getAreaId()> AreaConstant.TOTAL_PROVINCE){
                stationVO.setAreaId(null);
            }
            Station sta=BeanCopyUtil.copyObject(stationVO, Station.class);
            sta.setId(stationId);
            return sta;
        }
        return null;

    }
}
