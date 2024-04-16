package com.xzit.ordercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.order.model.dto.GoodsDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    IPage<GoodsDTO> getUserGoodsByQuery(IPage<GoodsDTO> page,@Param("queryVO") QueryVO queryVO,@Param("userInfoId") Long userInfoId);
    IPage<GoodsDTO> getAllGoodsByQuery(IPage<GoodsDTO> page,@Param("queryVO") QueryVO queryVO);
    IPage<GoodsDTO> getUncheckedGoodsByQuery(IPage<GoodsDTO> page,@Param("queryVO") QueryVO queryVO);
    Goods getGoodsByOrderNum(@Param("orderNum") String orderNum);
}
