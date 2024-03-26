package com.xzit.common.logistics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CenterDTO {
    Long id;
    String name;
    String province;
    String address;
    Double longitude;
    Double latitude;
    Boolean isDisable;
    Double maxSpace;
    Double freeSpace;
    LocalDateTime createTime;
    LocalDateTime updateTime;

}
