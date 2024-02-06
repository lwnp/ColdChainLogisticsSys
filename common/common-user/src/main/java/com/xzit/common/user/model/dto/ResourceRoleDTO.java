package com.xzit.common.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRoleDTO {
    Long id;
    String url;
    String requestMethod;
    List<String> roleList;
}
