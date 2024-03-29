package com.xzit.logisticscenter.mapper;


import com.xzit.common.logistics.model.dto.AvailableLogisticDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogisticMapper{
    List<AvailableLogisticDTO> getAvailableLogisticList(@Param("areaId") Long areaId,@Param("typeId") Long typeId);
}
