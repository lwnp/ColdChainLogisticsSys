package com.xzit.logisticscenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.entity.LimitTemp;
import com.xzit.common.logistics.model.dto.LimitTempDTO;
import com.xzit.common.logistics.model.vo.LimitTempVO;
import com.xzit.common.sys.model.vo.QueryVO;

public interface LimitTempService {
    IPage<LimitTempDTO> getLimitTempByQuery(QueryVO queryVO);
    void modifyLimitTemp(LimitTempVO limitTempVO);
    LimitTemp getLimitTempById(Long id);
}
