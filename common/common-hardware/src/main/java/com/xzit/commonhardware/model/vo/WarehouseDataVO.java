package com.xzit.commonhardware.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDataVO {
    Long centerId;
    Double temperature;
    Double humidity;
    Double dioxide;
    Double methane;
    Double oxide;
    LocalDateTime createTime;
}
