package com.xzit.hardwarecenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.common.hardware.model.dto.WarehouseDataDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarehouseMapper extends BaseMapper<WarehouseMapper> {
    void batchInsert(List<WarehouseData> dataList);
    List<WarehouseDataDTO> getWarehouseData(@Param("orderNum") String orderNum);
}
