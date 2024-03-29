package com.xzit.common.logistics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticFlowDTO {
    Long id;
    String orderNum;
    String description;
    Double weight;
    List<String> images;
    LocalDateTime timeStamp;

}
