package com.xzit.logisticscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.model.dto.ArrangementDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArrangementMapper extends BaseMapper<Arrangement> {
    void batchInsert(List<Arrangement> arrangements);
    IPage<ArrangementDTO> getArrangementByQuery(IPage<ArrangementDTO> page,@Param("queryVO") QueryVO queryVO);
    List<ArrangementDTO> getArrangementByUserInfoId(@Param("userInfoId") Long userInfoId);
    IPage<ArrangementDTO> getHistoryArrangementByQuery(IPage<ArrangementDTO> page,@Param("queryVO") QueryVO queryVO,@Param("userInfoId") Long userInfoId);
}
