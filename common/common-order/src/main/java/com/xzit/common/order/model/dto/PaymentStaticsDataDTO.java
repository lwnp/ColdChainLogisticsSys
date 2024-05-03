package com.xzit.common.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStaticsDataDTO {
    BigDecimal totalAmount;
    Long totalOrder;
    Long totalCompleted;
    Long inDelivery;
}
