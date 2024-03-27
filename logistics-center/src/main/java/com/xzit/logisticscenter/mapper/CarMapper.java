package com.xzit.logisticscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.logistics.entity.Car;
import com.xzit.common.logistics.model.dto.CarDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarMapper extends BaseMapper<Car> {
    IPage<CarDTO> getActiveCarByQuery(Page<CarDTO> page,@Param("queryVO") QueryVO queryVO);
    IPage<CarDTO> getDisableCarByQuery(Page<CarDTO> page,@Param("queryVO") QueryVO queryVO);

}
