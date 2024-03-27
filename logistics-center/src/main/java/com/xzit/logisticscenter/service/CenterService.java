package com.xzit.logisticscenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.model.dto.CenterDTO;
import com.xzit.common.logistics.model.vo.CenterVO;
import com.xzit.common.sys.model.vo.QueryVO;

public interface CenterService {
    Boolean addLogisticsCenter(CenterVO centerVO);
    IPage<CenterDTO> getActiveCenterByQuery(QueryVO queryVO);
    Boolean disableCenter(Long id);
    IPage<CenterDTO> getDisableCenterByQuery(QueryVO queryVO);
    Boolean activeCenter(Long id);
    Boolean modifyCenter(CenterVO centerVO,Long centerId);

}
