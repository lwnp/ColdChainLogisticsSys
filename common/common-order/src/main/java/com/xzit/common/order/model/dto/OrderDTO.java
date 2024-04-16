package com.xzit.common.order.model.dto;

import com.xzit.common.logistics.entity.AddressInfo;
import com.xzit.common.logistics.model.dto.AddressInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDTO {
    Long id;
    String username;
    String orderNum;
    String goodsName;
    Boolean isPay;
    BigDecimal price;
    String senderName;
    String receiverName;
    String senderAddress;
    String receiverAddress;
    String senderPhone;
    String receiverPhone;
    Boolean isActive;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
