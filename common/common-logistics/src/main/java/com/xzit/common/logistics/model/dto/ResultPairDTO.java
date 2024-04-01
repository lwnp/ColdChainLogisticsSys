package com.xzit.common.logistics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultPairDTO {
    AvailableLogisticDTO station;
    AvailableLogisticDTO center;
    Double distance;
}
