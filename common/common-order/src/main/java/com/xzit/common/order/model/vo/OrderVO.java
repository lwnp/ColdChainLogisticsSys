package com.xzit.common.order.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "订单")
public class OrderVO {
    @Schema(name = "userInfoId", description = "用户信息id",requiredMode = Schema.RequiredMode.REQUIRED,type = "long")
    Long userInfoId;
    @Schema(name = "goodsId", description = "货物id",requiredMode = Schema.RequiredMode.REQUIRED,type = "long")
    Long goodsId;
    @Schema(name = "senderAddressId", description = "发货地址id",requiredMode = Schema.RequiredMode.REQUIRED,type = "long")
    Long senderAddressId;
    @Schema(name = "receiveAddressId", description = "收货地址id",requiredMode = Schema.RequiredMode.REQUIRED,type = "long")
    Long receiveAddressId;
}
