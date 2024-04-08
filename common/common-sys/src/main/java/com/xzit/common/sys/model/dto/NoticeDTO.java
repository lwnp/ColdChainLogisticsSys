package com.xzit.common.sys.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {
    Long id;
    String username;
    Boolean isRead;
    String title;
    String content;
    LocalDateTime createTime;
}
