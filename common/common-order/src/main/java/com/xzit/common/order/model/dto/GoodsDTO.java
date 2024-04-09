package com.xzit.common.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {
    Long id;
    String username;
    String goodsName;
    String checkStatusName;
    String description;
    String image;
    Double weight;
    Double space;
    Double maxTemperature;
    Double minTemperature;
    Double maxDioxide;
    Double minDioxide;
    Double maxHumidity;
    Double minHumidity;
    Double maxOxide;
    Double minOxide;
    Double maxMethane;
    Double minMethane;
    Double maxEthylene;
    Double minEthylene;
    LocalDateTime createTime;
    LocalDateTime updateTime;

}
