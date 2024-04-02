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
@Schema(description = "货物")
public class GoodsVO {
    @Schema(name = "userInfoId", description = "用户信息id",type = "string",requiredMode = Schema.RequiredMode.AUTO)
    Long userInfoId;
    @Schema(name = "name", description = "货物名称",type = "string",requiredMode = Schema.RequiredMode.AUTO)
    String name;
    @Schema(name = "description", description = "货物描述",type = "string",requiredMode = Schema.RequiredMode.AUTO)
    String description;
    @Schema(name = "image", description = "货物图片",type = "string",requiredMode = Schema.RequiredMode.AUTO)
    String image;
    @Schema(name = "weight", description = "货物重量",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double weight;
    @Schema(name = "space", description = "货物空间",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double space;
    @Schema(name = "temperature", description = "货物最高温度",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double maxTemperature;
    @Schema(name = "temperature", description = "货物最低温度",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double minTemperature;
    @Schema(name = "dioxide", description = "货物最高二氧化碳",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double maxDioxide;
    @Schema(name = "dioxide", description = "货物最低二氧化碳",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double minDioxide;
    @Schema(name = "humidity", description = "货物最高湿度",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double maxHumidity;
    @Schema(name = "humidity", description = "货物最低湿度",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double minHumidity;
}
