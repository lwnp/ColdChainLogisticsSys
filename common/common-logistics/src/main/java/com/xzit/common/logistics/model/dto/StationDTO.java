package com.xzit.common.logistics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationDTO {
    Long id;
    String name;
    String address;
    Double longitude;
    Double latitude;
    String province;
    Boolean isDisable;
    LocalDateTime createTime;
    LocalDateTime updateTime;

}
