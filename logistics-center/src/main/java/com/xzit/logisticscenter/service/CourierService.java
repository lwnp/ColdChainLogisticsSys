package com.xzit.logisticscenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.model.dto.CourierDTO;
import com.xzit.common.logistics.model.vo.CourierVO;
import com.xzit.common.sys.model.vo.QueryVO;

public interface CourierService {
    Boolean addCourier(CourierVO courierVO);
    Boolean disableCourier(Long courierId);
    Boolean activeCourier(Long courierId);
    IPage<CourierDTO> getActiveCourierByQuery(QueryVO queryVO);
    IPage<CourierDTO> getDisableCourierByQuery(QueryVO queryVO);
    Boolean modifyCourier(CourierVO courierVO,Long courierId);
    Boolean isBindCourier(Long userInfoId);
}
