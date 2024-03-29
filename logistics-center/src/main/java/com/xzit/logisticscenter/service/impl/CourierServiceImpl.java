package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.api.user.feign.RoleFeignClient;
import com.xzit.common.logistics.entity.Center;
import com.xzit.common.logistics.entity.Courier;
import com.xzit.common.logistics.entity.Station;
import com.xzit.common.logistics.model.dto.CourierDTO;
import com.xzit.common.logistics.model.vo.CarVO;
import com.xzit.common.logistics.model.vo.CourierVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.logisticscenter.mapper.CenterMapper;
import com.xzit.logisticscenter.mapper.CourierMapper;
import com.xzit.logisticscenter.mapper.StationMapper;
import com.xzit.logisticscenter.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final CourierMapper courierMapper;
    private final CenterMapper centerMapper;
    private final StationMapper stationMapper;
    private final RoleFeignClient roleFeignClient;
    @Override
    public Boolean addCourier(CourierVO courierVO) {
        QueryWrapper<Courier> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_info_id",courierVO.getUserInfoId());
        if(courierMapper.exists(queryWrapper)){
            return false;
        }
        if(!roleFeignClient.isValidCourier(courierVO.getUserInfoId()).getData()){
            return false;
        }
        if(isNotExistLogisticId(courierVO)){
            return false;
        }
        Courier courier= BeanCopyUtil.copyObject(courierVO,Courier.class);

        return courierMapper.insert(courier)==1;
    }

    @Override
    public Boolean disableCourier(Long courierId) {
        Courier  courier=courierMapper.selectById(courierId);
        if(courier==null||courier.getIsDisable()){
            return false;
        }
        courier.setIsDisable(true);
        return courierMapper.updateById(courier)==1;
    }

    @Override
    public Boolean activeCourier(Long courierId) {
        Courier courier=courierMapper.selectById(courierId);
        if(courier==null||!courier.getIsDisable()){
            return false;
        }
        courier.setIsDisable(false);
        return courierMapper.updateById(courier)==1;
    }

    @Override
    public IPage<CourierDTO> getActiveCourierByQuery(QueryVO queryVO) {
        Page<CourierDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return courierMapper.getActiveCourierByQuery(page, queryVO);
    }

    @Override
    public IPage<CourierDTO> getDisableCourierByQuery(QueryVO queryVO) {
        Page<CourierDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return courierMapper.getDisableCourierByQuery(page, queryVO);
    }

    @Override
    public Boolean modifyCourier(CourierVO courierVO, Long courierId) {
        Courier courier=isValid(courierVO,courierId);
        if(courier==null){
            return false;
        }
        return courierMapper.updateById(courier)==1;

    }

    @Override
    public Boolean isBindCourier(Long userInfoId) {
        QueryWrapper<Courier> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_info_id",userInfoId);
        return courierMapper.exists(queryWrapper);
    }

    private boolean isNotExistLogisticId(CourierVO courierVO){
        QueryWrapper<Center> centerQueryWrapper=new QueryWrapper<>();
        QueryWrapper<Station> stationQueryWrapper=new QueryWrapper<>();
        centerQueryWrapper.eq("id",courierVO.getLogisticId());
        stationQueryWrapper.eq("id",courierVO.getLogisticId());
        if (courierVO.getTypeId()==1&&centerMapper.exists(centerQueryWrapper)){
            return false;
        }
        return courierVO.getTypeId() != 2 || !stationMapper.exists(stationQueryWrapper);
    }
    private Courier isValid(CourierVO courierVO,Long courierId){
        Courier courier=courierMapper.selectById(courierId);
        if(courier==null){
            return null;
        }
        if(courierVO.getLogisticId()==null&&courierVO.getTypeId()==null){
            Courier courierData=BeanCopyUtil.copyObject(courierVO,Courier.class);
            courierData.setId(courierId);
            return courierData;
        } else if (isNotExistLogisticId(courierVO)) {
            return null;

        }
        else {
            Courier courierData=BeanCopyUtil.copyObject(courierVO,Courier.class);
            courierData.setId(courierId);
            return courierData;
        }
    }
}
