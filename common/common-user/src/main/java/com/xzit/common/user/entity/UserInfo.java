package com.xzit.common.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    Long id;
    String nickname;
    String avatar;
    String email;
    String phone;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
