package com.xzit.common.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class UserInfoDTO {
    Long id;
    String username;
    Boolean isDisable;
    List<String> roles;
    String nickname;
    String avatar;
    String email;
    String phone;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
