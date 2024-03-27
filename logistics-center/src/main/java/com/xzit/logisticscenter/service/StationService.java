package com.xzit.logisticscenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.model.dto.StationDTO;
import com.xzit.common.logistics.model.vo.StationVO;
import com.xzit.common.sys.model.vo.QueryVO;

public interface StationService {
    Boolean addLogisticStation(StationVO stationVO);
    IPage<StationDTO> getActiveStation(QueryVO queryVO);
    Boolean disableStation(Long stationId);
    IPage<StationDTO> getDisableStation(QueryVO queryVO);
    Boolean activeStation(Long stationId);
    Boolean modifyStation(StationVO stationVO,Long stationId);
}
