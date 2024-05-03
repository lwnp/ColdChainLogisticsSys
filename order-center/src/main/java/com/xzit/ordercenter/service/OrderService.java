package com.xzit.ordercenter.service;


import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.model.dto.AreaStaticsDataDTO;
import com.xzit.common.order.model.dto.OrderDTO;
import com.xzit.common.order.model.dto.OrderStaticsDataDTO;
import com.xzit.common.order.model.dto.PaymentStaticsDataDTO;
import com.xzit.common.order.model.vo.OrderVO;
import com.xzit.common.sys.model.vo.QueryVO;

import java.util.List;
import java.util.function.Function;

public interface OrderService {
    Order getOrderByOrderNum(String orderNum);
    String generateAlipayUrl(OrderVO orderVO);
    void updatePayStatus(String orderNum);
    IPage<OrderDTO> getOrderByQuery(QueryVO queryVO);
    IPage<OrderDTO> getActiveOderByQuery(QueryVO queryVO);
    IPage<OrderDTO> getUserOrderByQuery(QueryVO queryVO);
    IPage<OrderDTO> getUserActiveOrderByQuery(QueryVO queryVO);
    OrderDTO getUnpaidOrder();
    void finishOrder(String orderNum);
    IPage<OrderDTO> getReceiverOrderByQuery(QueryVO queryVO);
    IPage<OrderDTO> getReceiverHistoryOrderByQuery(QueryVO queryVO);
    void deleteOrder(Long orderId);
    String continuePay(String orderNum);
    List<Order> getInStoreOrder();
    List<OrderStaticsDataDTO> getOrderStaticsData();
    PaymentStaticsDataDTO getPaymentStaticsData();
    List<AreaStaticsDataDTO> getAreaStaticsData(Long areaId);
}
