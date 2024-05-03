package com.xzit.ordercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.model.dto.OrderDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    IPage<OrderDTO> getOrderByQuery(Page<OrderDTO> page, @Param("queryVO") QueryVO queryVO);
    IPage<OrderDTO> getActiveOderByQuery(Page<OrderDTO> page, @Param("queryVO") QueryVO queryVO);
    IPage<OrderDTO> getUserOrderByQuery(Page<OrderDTO> page, @Param("queryVO") QueryVO queryVO,@Param("userInfoId") Long userInfoId);
    IPage<OrderDTO> getUserActiveOrderByQuery(Page<OrderDTO> page, @Param("queryVO") QueryVO queryVO,@Param("userInfoId") Long userInfoId);
    OrderDTO getUnpaidOrder(@Param("userInfoId") Long userInfoId);
    IPage<OrderDTO> getReceiverOrderByQuery(Page<OrderDTO> page, @Param("queryVO") QueryVO queryVO,@Param("userInfoId") Long userInfoId);
    IPage<OrderDTO> getReceiverHistoryOrderByQuery(Page<OrderDTO> page, @Param("queryVO") QueryVO queryVO,@Param("userInfoId") Long userInfoId);
    List<Order> getInStoreOrder();
}
