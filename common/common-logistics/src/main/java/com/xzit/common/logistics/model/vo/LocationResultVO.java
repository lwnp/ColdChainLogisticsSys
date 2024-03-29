package com.xzit.common.logistics.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResultVO {
    Integer status;
    String message;
    Address2LocationResultVO result;
    
}
