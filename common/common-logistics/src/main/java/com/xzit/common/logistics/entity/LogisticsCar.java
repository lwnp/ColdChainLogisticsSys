package com.xzit.common.logistics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsCar {
    Long id;
    Long centerId;
    String number;
    Integer maxLoad;  //最大载重
    Integer maxSpace; //最大运载体积
}
