package com.xzit.logisticscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.logistics.entity.Center;
import com.xzit.common.logistics.model.dto.CenterDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CenterMapper extends BaseMapper<Center> {
    IPage<CenterDTO> getAllByQuery(Page<CenterDTO> page,@Param("queryVO") QueryVO queryVO);

}
