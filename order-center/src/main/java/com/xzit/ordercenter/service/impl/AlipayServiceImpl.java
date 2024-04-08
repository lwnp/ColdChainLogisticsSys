package com.xzit.ordercenter.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.xzit.common.order.constant.AlipayConstant;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.model.dto.AlipayDTO;
import com.xzit.ordercenter.service.AlipayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlipayServiceImpl implements AlipayService {
    private final AlipayClient  alipayClient;
    private final AlipayTradePagePayRequest request;
    @Override
    public String generatePaymentUrl(Order order){
        AlipayDTO alipayDTO=AlipayDTO.builder()
                .out_trade_no(order.getOrderNum())
                .product_code(AlipayConstant.PRODUCT_CODE)
                .total_amount(order.getPrice().toPlainString())
                .subject(AlipayConstant.PAYMENT_SUBJECT)
                .build();
        request.setBizContent(JSON.toJSONString(alipayDTO));
        try{
            return alipayClient.pageExecute(request, "GET").getBody();
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return null;
    }
}
