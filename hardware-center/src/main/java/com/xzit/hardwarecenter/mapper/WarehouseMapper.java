package com.xzit.hardwarecenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.commonhardware.entity.WarehouseData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WarehouseMapper extends BaseMapper<WarehouseMapper> {
    void batchInsert(List<WarehouseData> dataList);
}
