package com.xzit.hardwarecenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.hardware.entity.IOTData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IOTMapper extends BaseMapper<IOTData> {
    void batchInsert(List<IOTData> iotDataList);
}
