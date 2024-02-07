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
public class MenuDTO {
    Long id;
    String name;
    String path;
    List<MenuDTO> childMenu;
    LocalDateTime creatTime;
    LocalDateTime updateTime;
}
