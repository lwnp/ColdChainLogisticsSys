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
    @Schema(name = "maxTemperature", description = "货物最高温度",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double maxTemperature;
    @Schema(name = "minTemperature", description = "货物最低温度",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double minTemperature;
    @Schema(name = "maxDioxide", description = "货物最高二氧化碳",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double maxDioxide;
    @Schema(name = "minDioxide", description = "货物最低二氧化碳",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double minDioxide;
    @Schema(name = "maxHumidity", description = "货物最高湿度",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double maxHumidity;
    @Schema(name = "minHumidity", description = "货物最低湿度",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double minHumidity;
    @Schema(name = "maxOxide", description = "货物最高一氧化碳",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double maxOxide;
    @Schema(name = "minOxide", description = "货物最低一氧化碳",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double minOxide;
    @Schema(name = "maxMethane", description = "货物最高甲烷",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double maxMethane;
    @Schema(name = "minMethane", description = "货物最低甲烷",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double minMethane;
    @Schema(name = "maxEthylene", description = "货物最高乙烯",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double maxEthylene;
    @Schema(name = "minEthylene", description = "货物最低乙烯",type = "double",requiredMode = Schema.RequiredMode.AUTO)
    Double minEthylene;
}
