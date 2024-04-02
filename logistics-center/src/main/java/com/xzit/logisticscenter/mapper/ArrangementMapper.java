package com.xzit.logisticscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.common.logistics.entity.Arrangement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArrangementMapper extends BaseMapper<Arrangement> {
    void batchInsert(List<Arrangement> arrangements);
}
