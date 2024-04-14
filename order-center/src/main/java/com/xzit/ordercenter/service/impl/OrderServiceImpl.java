package com.xzit.ordercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzit.api.logistics.feign.AddressInfoFeignClient;
import com.xzit.api.logistics.feign.ArrangementFeignClient;
import com.xzit.api.logistics.feign.FeeStatesFeignClient;
import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.common.logistics.model.dto.AddressInfoDTO;
import com.xzit.common.logistics.model.dto.ArrangeDistanceDTO;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.model.vo.OrderVO;
import com.xzit.common.order.utils.FeeCalculator;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.user.entity.AuthUser;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.ordercenter.mapper.GoodsMapper;
import com.xzit.ordercenter.mapper.OrderMapper;
import com.xzit.ordercenter.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final GoodsMapper goodsMapper;
    private final OrderMapper orderMapper;
    private final AddressInfoFeignClient  addressInfoFeignClient;
    private final FeeStatesFeignClient  feeStatesFeignClient;
    private final ArrangementFeignClient arrangementFeignClient;
    private final UserFeignClient userFeignClient;
    private final StreamBridge streamBridge;

    public Function<OrderVO, Order> createOrder() {
        return orderVO -> {
            if(orderVO.getGoodsId()==null||orderVO.getUserInfoId()==null||orderVO.getReceiveAddressId()==null||orderVO.getSenderAddressId()==null){
                throw new BizException("参数不能为空");
            }
            Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Map<String,Object> map=jwt.getClaims();
            Long userId= (Long) map.get("userId");
            UserInfoDTO  userInfoDTO=userFeignClient.getUserInfo(userId).getData();
            Goods goods = goodsMapper.selectById(orderVO.getGoodsId());
            if(!Objects.equals(orderVO.getUserInfoId(), userInfoDTO.getId())){
                throw new BizException("非法操作");
            }
            if(goods==null||goods.getStatusId()!=2||!goods.getUserInfoId().equals(userInfoDTO.getId())){
                throw new BizException("货物不存在或未通过审核");
            }
            AddressInfo sendAddress = addressInfoFeignClient.getAddressInfo(orderVO.getSenderAddressId()).getData();
            if(sendAddress==null|| !Objects.equals(sendAddress.getUserInfoId(), userInfoDTO.getId())){
                throw new BizException("发件地址不存在");
            }
            AddressInfo receiveAddress = addressInfoFeignClient.getAddressInfo(orderVO.getReceiveAddressId()).getData();
            if(receiveAddress==null|| !Objects.equals(receiveAddress.getUserInfoId(), userInfoDTO.getId())){
                throw new BizException("收件地址不存在");
            }
            QueryWrapper<Order>  queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("user_info_id",orderVO.getUserInfoId()).and(q->q.eq("is_pay",0).and(r->r.eq("is_active",1)));
            List<Order> orderList=orderMapper.selectList(queryWrapper);
            if(!orderList.isEmpty()){
                 throw new BizException("您已经有未支付的订单");
            }
            List<FeeStates>  feeStatesList=feeStatesFeignClient.getAllFeeStates().getData();
            if(feeStatesList.isEmpty()){
                 throw new BizException("运费规则不存在");
            }
            ArrangeDistanceDTO arrangeDistanceDTO =arrangementFeignClient.getArrangementList(sendAddress.getId(),receiveAddress.getId(),goods.getId()).getData();
            List<Arrangement> arrangementList=arrangeDistanceDTO.getArrangementList();
            if(arrangementList.isEmpty()){
                throw new BizException("当前地区运力不足，请更换始发地或终到地再试");
            }
            String orderNum= UUID.randomUUID().toString().replace("-", "");
            BigDecimal price= FeeCalculator.calculateShippingCost((arrangeDistanceDTO.getDistance().intValue()+1)*2,goods.getWeight().intValue()+1,goods.getSpace().intValue()+1,feeStatesList);
                Order order=Order.builder()
                        .price(price)
                        .goodsId(orderVO.getGoodsId())
                        .receiveAddressId(orderVO.getReceiveAddressId())
                        .senderAddressId(orderVO.getSenderAddressId())
                        .userInfoId(orderVO.getUserInfoId())
                        .orderNum(orderNum)
                        .build();
                orderMapper.insert(order);
                arrangementList.forEach(a-> a.setOrderId(order.getId()));
                return order;
        };
    }
}
