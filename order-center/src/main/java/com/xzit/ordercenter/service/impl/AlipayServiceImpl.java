package com.xzit.ordercenter.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.xzit.common.order.constant.AlipayConstant;
import com.xzit.common.order.entity.Order;
import com.xzit.common.order.enums.TimeZoneEnums;
import com.xzit.common.order.model.dto.AlipayDTO;
import com.xzit.ordercenter.service.AlipayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlipayServiceImpl implements AlipayService {
    private final AlipayClient  alipayClient;
    private final AlipayTradePagePayRequest request;
    private final AlipayTradeQueryRequest queryRequest;

    @Override
    public String generatePaymentUrl(Order order){
        ZoneId zoneId = ZoneId.of(TimeZoneEnums.CTT.getZoneIdName());
        AlipayDTO alipayDTO=AlipayDTO.builder()
                .out_trade_no(order.getOrderNum())
                .product_code(AlipayConstant.PRODUCT_CODE)
                .total_amount(order.getPrice().toPlainString())
                .subject(AlipayConstant.PAYMENT_SUBJECT)
                .time_expire(LocalDateTime.now(zoneId).plusMinutes(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        request.setBizContent(JSON.toJSONString(alipayDTO));
        try{
            return alipayClient.pageExecute(request, "GET").getBody();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean isPaid(String orderNum) {
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(orderNum);
        queryRequest.setBizModel(model);
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(queryRequest);
            JSONObject jsonResponse = JSONObject.parseObject(response.getBody());
            String tradeStatus = jsonResponse.getJSONObject("alipay_trade_query_response").getString("trade_status");
            return "TRADE_SUCCESS".equals(tradeStatus);
        }catch (Exception e){
            return false;
        }
    }
}
