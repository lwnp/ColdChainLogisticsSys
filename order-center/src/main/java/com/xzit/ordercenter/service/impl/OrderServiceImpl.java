package com.xzit.ordercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.api.logistics.feign.ArrangementFeignClient;
import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.model.dto.OrderDTO;
import com.xzit.common.order.model.vo.OrderVO;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.ordercenter.mapper.GoodsMapper;
import com.xzit.ordercenter.mapper.OrderMapper;
import com.xzit.ordercenter.service.AlipayService;
import com.xzit.ordercenter.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final GoodsMapper goodsMapper;
    private final OrderMapper orderMapper;
    private final UserFeignClient userFeignClient;
    private final ArrangementFeignClient arrangementFeignClient;
    private final AlipayService alipayService;
    private final RabbitTemplate rabbitTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String generateAlipayUrl(OrderVO orderVO) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map = jwt.getClaims();
        Long userId = (Long) map.get("userId");
        UserInfoDTO userInfoDTO = userFeignClient.getUserInfo(userId).getData();

        if (orderVO.getGoodsId() == null || orderVO.getUserInfoId() == null || orderVO.getReceiveAddressId() == null || orderVO.getSenderAddressId() == null) {
            throw new BizException("参数不能为空");
        }

        Goods goods = goodsMapper.selectById(orderVO.getGoodsId());
        if (!Objects.equals(orderVO.getUserInfoId(), userInfoDTO.getId())) {
            throw new BizException("非法操作");
        }
        if (goods == null || goods.getStatusId() != 2 || !goods.getUserInfoId().equals(userInfoDTO.getId())) {
            throw new BizException("货物不存在或未通过审核");
        }
        if (orderVO.getSenderAddressId() == null) {
            throw new BizException("发件地址不能为空");
        }
        if (orderVO.getReceiveAddressId() == null) {
            throw new BizException("收件地址不能为空");
        }

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_info_id", orderVO.getUserInfoId()).and(q -> q.eq("is_pay", 0).and(r -> r.eq("is_active", 1)));
        List<Order> orderList = orderMapper.selectList(queryWrapper);
        if (!orderList.isEmpty()) {
            throw new BizException("您已经有未支付的订单");
        }

        String orderNum = UUID.randomUUID().toString().replaceAll("-", "");

        Order order = Order.builder()
                .goodsId(orderVO.getGoodsId())
                .receiveAddressId(orderVO.getReceiveAddressId())
                .senderAddressId(orderVO.getSenderAddressId())
                .userInfoId(orderVO.getUserInfoId())
                .orderNum(orderNum)
                .build();
        orderMapper.insert(order);
        BigDecimal price=null;
        try {
            price = arrangementFeignClient.arrangeOrder(order.getOrderNum()).getData();
        }catch (Exception ignored){
        }
        if (price == null) {
            orderMapper.deleteById(order.getId());
            throw new BizException("当前系统运力不足，请更换始发地或终到地再试");
        }

        order.setPrice(price.setScale(2, RoundingMode.DOWN));
        orderMapper.updateById(order);

        rabbitTemplate.convertAndSend("order-exchange", "order-create", order);
        try{
            String payUrl = alipayService.generatePaymentUrl(order);
            redisTemplate.opsForValue().set(orderNum,payUrl,5, TimeUnit.MINUTES);
            return payUrl;
        }catch (Exception e){
            orderMapper.deleteById(order.getId());
            return null;
        }


    }

    @Override
    public void updatePayStatus(String orderNum) {
        Order order=orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderNum));
        order.setIsPay(true);
        orderMapper.updateById(order);
    }

    @Override
    public IPage<OrderDTO> getOrderByQuery(QueryVO queryVO) {
        Page<OrderDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return orderMapper.getOrderByQuery(page, queryVO);
    }

    @Override
    public IPage<OrderDTO> getActiveOderByQuery(QueryVO queryVO) {
        Page<OrderDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return orderMapper.getActiveOderByQuery(page, queryVO);
    }

    @Override
    public IPage<OrderDTO> getUserOrderByQuery(QueryVO queryVO) {
        UserInfoDTO userInfoDTO=getUserInfo();
        Page<OrderDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return orderMapper.getUserOrderByQuery(page, queryVO,userInfoDTO.getId());
    }

    @Override
    public IPage<OrderDTO> getUserActiveOrderByQuery(QueryVO queryVO) {
        UserInfoDTO userInfoDTO=getUserInfo();
        Page<OrderDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return orderMapper.getUserActiveOrderByQuery(page, queryVO,userInfoDTO.getId());
    }

    @Override
    public OrderDTO getUnpaidOrder() {
        UserInfoDTO userInfoDTO=getUserInfo();
        return orderMapper.getUnpaidOrder(userInfoDTO.getId());
    }

    @Override
    public void finishOrder(String orderNum) {
        Order order=orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderNum));
        order.setIsActive(false);
        orderMapper.updateById(order);
    }

    @Override
    public IPage<OrderDTO> getReceiverOrderByQuery(QueryVO queryVO) {
        UserInfoDTO userInfoDTO = getUserInfo();
        Page<OrderDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return orderMapper.getReceiverOrderByQuery(page, queryVO,userInfoDTO.getId());
    }

    @Override
    public IPage<OrderDTO> getReceiverHistoryOrderByQuery(QueryVO queryVO) {
        UserInfoDTO userInfoDTO = getUserInfo();
        Page<OrderDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return orderMapper.getReceiverHistoryOrderByQuery(page, queryVO,userInfoDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(Long orderId) {
        Order order=orderMapper.selectById(orderId);
        UserInfoDTO userInfoDTO = getUserInfo();
        if(order.getIsActive()||!order.getUserInfoId().equals(userInfoDTO.getId())){
            throw new BizException("非法操作");
        }else{
            orderMapper.deleteById(orderId);
        }
    }

    @Override
    public String continuePay(String orderNum) {
        String payUrl=redisTemplate.opsForValue().get(orderNum);
        if (payUrl==null){
            throw new BizException("订单不存在或已支付");
        }
        return payUrl;
    }

    @Override
    public Order getOrderByOrderNum(String orderNum) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_num", orderNum);
        return orderMapper.selectOne(queryWrapper);
    }
    private UserInfoDTO getUserInfo(){
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        return userFeignClient.getUserInfo(userId).getData();
    }
}
