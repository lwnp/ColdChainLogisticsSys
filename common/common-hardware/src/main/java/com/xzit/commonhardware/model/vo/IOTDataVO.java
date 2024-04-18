package com.xzit.commonhardware.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IOTDataVO {
    Long arrangementId;
    Double longitude;
    Double latitude;
    LocalDateTime createTime;
}
