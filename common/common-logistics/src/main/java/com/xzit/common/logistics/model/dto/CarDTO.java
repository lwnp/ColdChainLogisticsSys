package com.xzit.common.logistics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    Long id;
    String number;
    String belong;
    String name;
    Boolean isInUse;
    Boolean isDisable;
    Double maxLoad;
    Double maxSpace;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
