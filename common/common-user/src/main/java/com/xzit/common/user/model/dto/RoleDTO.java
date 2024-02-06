package com.xzit.common.user.model.dto;

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
public class RoleDTO {
    Long id;
    String name;
    List<Long> menuIds;
    List<Long> resourceIds;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
