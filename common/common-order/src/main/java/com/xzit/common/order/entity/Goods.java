package com.xzit.common.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_order_goods")
public class Goods {
    @TableId(type = IdType.AUTO)
    Long id;
    Long userInfoId;
    String name;
    String description;
    String image;
    Long statusId;
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    LocalDateTime createTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    LocalDateTime updateTime;
}
