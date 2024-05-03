package com.xzit.common.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStaticsDataDTO {
    String month;
    Long orderCount;
    Long paymentCount;
}
