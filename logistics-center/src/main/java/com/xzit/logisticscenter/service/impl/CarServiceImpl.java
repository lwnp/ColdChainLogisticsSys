package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.logistics.entity.Car;
import com.xzit.common.logistics.entity.Center;
import com.xzit.common.logistics.entity.Station;
import com.xzit.common.logistics.model.dto.CarDTO;
import com.xzit.common.logistics.model.vo.CarVO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.logisticscenter.mapper.CarMapper;
import com.xzit.logisticscenter.mapper.CenterMapper;
import com.xzit.logisticscenter.mapper.StationMapper;
import com.xzit.logisticscenter.service.CarService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarMapper carMapper;
    private final CenterMapper centerMapper;
    private final StationMapper stationMapper;
    @Override
    public Boolean addCar(CarVO carVO) {
        if(isNotExistLogisticId(carVO)){
            return false;
        }
        Car car= BeanCopyUtil.copyObject(carVO, Car.class);
        return carMapper.insert(car)==1;
    }

    @Override
    public IPage<CarDTO> getActiveCarByQuery(QueryVO queryVO) {
        Page<CarDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return carMapper.getActiveCarByQuery(page,queryVO);
    }

    @Override
    public IPage<CarDTO> getDisableCarByQuery(QueryVO queryVO) {
        Page<CarDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return carMapper.getDisableCarByQuery(page,queryVO);
    }

    @Override
    public Boolean modifyCar(CarVO carVO,Long carId) {
        Car car=isValid(carVO,carId);
        if(car==null){
            return false;
        }
        return carMapper.updateById(car)==1;
    }

    @Override
    public Boolean disableCar(Long carId) {
        Car car=carMapper.selectById(carId);
        if(car==null||car.getIsDisable()){
            return false;
        }
        car.setIsDisable(true);
        return carMapper.updateById(car)==1;
    }

    @Override
    public Boolean activeCar(Long carId) {
        Car car=carMapper.selectById(carId);
        if(car==null||!car.getIsDisable()){
            return false;
        }
        car.setIsDisable(false);
        return carMapper.updateById(car)==1;
    }
    private Car isValid(CarVO carVO,Long carId){
        Car car=carMapper.selectById(carId);
        if (car==null){
            return null;
        }
        if(carVO.getLogisticId()==null&&carVO.getTypeId()==null){
            if(StringUtils.isEmpty(carVO.getNumber())){
                carVO.setNumber(null);
            }
            Car carData=BeanCopyUtil.copyObject(carVO, Car.class);
            carData.setId(carId);
            return carData;
        }
        else if(isNotExistLogisticId(carVO)){
            return null;
        }
        else {
            if(StringUtils.isEmpty(carVO.getNumber())){
                carVO.setNumber(null);
            }
            Car carData=BeanCopyUtil.copyObject(carVO, Car.class);
            carData.setId(carId);
            return carData;
        }

    }
    private boolean isNotExistLogisticId(CarVO carVO){
        QueryWrapper<Center> centerQueryWrapper=new QueryWrapper<>();
        QueryWrapper<Station> stationQueryWrapper=new QueryWrapper<>();
        centerQueryWrapper.eq("id",carVO.getLogisticId());
        stationQueryWrapper.eq("id",carVO.getLogisticId());
        if (carVO.getTypeId()==1&&centerMapper.exists(centerQueryWrapper)){
            return false;
        }
        return carVO.getTypeId() != 2 || !stationMapper.exists(stationQueryWrapper);
    }
}
