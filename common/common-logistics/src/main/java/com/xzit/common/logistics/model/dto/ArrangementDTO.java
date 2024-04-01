package com.xzit.common.logistics.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrangementDTO {
    Long id;
    String orderNum;
    String status;
    String step;
    String carNum;
    String courierName;
    String belong;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
