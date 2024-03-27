package com.xzit.logisticscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.logistics.entity.Station;
import com.xzit.common.logistics.model.dto.StationDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StationMapper extends BaseMapper<Station> {
    IPage<StationDTO> getActiveStationByQuery(Page<StationDTO> page,@Param("queryVO") QueryVO queryVO);
    IPage<StationDTO> getDisableStationByQuery(Page<StationDTO> page,@Param("queryVO") QueryVO queryVO);
}
