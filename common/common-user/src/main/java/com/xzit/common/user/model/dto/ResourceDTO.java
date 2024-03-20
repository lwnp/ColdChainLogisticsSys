package com.xzit.common.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {
    Long id;
    String name;
    String url;
    String requestMethod;
    Boolean isAnonymous;
    List<String> roles;
    LocalDateTime createTime;
    LocalDateTime updateTime;

}
