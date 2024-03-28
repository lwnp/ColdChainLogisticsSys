package com.xzit.common.logistics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressInfoDTO {
    Long id;
    String province;
    String address;
    String name;
    String phone;
    String username;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
