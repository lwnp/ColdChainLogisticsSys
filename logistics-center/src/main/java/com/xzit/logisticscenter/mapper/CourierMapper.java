package com.xzit.logisticscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.logistics.entity.Courier;
import com.xzit.common.logistics.model.dto.CourierDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourierMapper extends BaseMapper<Courier> {
    IPage<CourierDTO> getActiveCourierByQuery(Page<CourierDTO> page,@Param("queryVO") QueryVO queryVO);
    IPage<CourierDTO> getDisableCourierByQuery(Page<CourierDTO> page,@Param("queryVO") QueryVO queryVO);
}
