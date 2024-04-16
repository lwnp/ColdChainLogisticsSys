package com.xzit.common.logistics.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "物流流信息")
public class LogisticFlowVO {
    @Schema(name = "orderNum",description = "订单号",requiredMode = Schema.RequiredMode.REQUIRED)
    String orderNum;
    @Schema(name = "description",description = "货物重量",requiredMode = Schema.RequiredMode.REQUIRED)
    Double weight;
    @Schema(name = "images",description = "实拍图片",requiredMode = Schema.RequiredMode.REQUIRED)
    List<String> images;
}