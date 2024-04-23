package com.xzit.common.hardware.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDataDTO {
    Long id;
    String name;
    Double temperature;
    Double humidity;
    Double dioxide;
    Double oxide;
    Double methane;
    LocalDateTime createTime;
}
