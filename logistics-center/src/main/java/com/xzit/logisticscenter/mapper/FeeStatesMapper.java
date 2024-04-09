package com.xzit.logisticscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.common.logistics.model.dto.FeeStatesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeeStatesMapper extends BaseMapper<FeeStates> {
    List<FeeStatesDTO> getFeeStates();

}
