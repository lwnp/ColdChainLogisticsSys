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
public class LimitTempDTO {
    Long id;
    String name;
    Double min;
    Double max;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
