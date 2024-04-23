package com.xzit.logisticscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.logistics.entity.LogisticFlow;
import com.xzit.common.logistics.model.dto.LogisticFlowDTO;
import com.xzit.common.logistics.model.dto.SimpleLogisticFlowDTO;
import com.xzit.commonhardware.entity.IOTData;
import com.xzit.commonhardware.entity.WarehouseData;
import com.xzit.commonhardware.model.dto.WarehouseDataDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogisticFlowMapper extends BaseMapper<LogisticFlow>{
    List<SimpleLogisticFlowDTO> getSimpleLogisticFlowByOrderNum(@Param("orderNum")String orderNum);
    List<LogisticFlowDTO> getLogisticFlowByOrderNum(@Param("orderNum") String orderNum);
    List<IOTData> getLocationDataByOrderNum(@Param("orderNum") String orderNum);
}
