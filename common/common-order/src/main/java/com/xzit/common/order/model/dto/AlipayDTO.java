package com.xzit.common.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlipayDTO {
    String out_trade_no;
    String subject;
    String total_amount;
    String product_code;
    String time_expire;
}
