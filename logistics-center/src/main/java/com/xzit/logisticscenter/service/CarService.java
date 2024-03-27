package com.xzit.logisticscenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.model.dto.CarDTO;
import com.xzit.common.logistics.model.vo.CarVO;
import com.xzit.common.sys.model.vo.QueryVO;

public interface CarService {
    Boolean addCar(CarVO carVO);
    IPage<CarDTO> getActiveCarByQuery(QueryVO queryVO);
    IPage<CarDTO> getDisableCarByQuery(QueryVO queryVO);
    Boolean modifyCar(CarVO carVO,Long carId);
    Boolean disableCar(Long carId);
    Boolean activeCar(Long carId);
}
