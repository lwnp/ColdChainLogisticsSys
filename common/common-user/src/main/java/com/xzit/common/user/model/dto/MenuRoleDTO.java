package com.xzit.common.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRoleDTO {
    String name;
    String path;
    List<MenuRoleDTO> childMenu;
}
