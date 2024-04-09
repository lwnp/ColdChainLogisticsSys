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
    Long carId;
    Double dioxide;
    Double temperature;
    Double humidity;
    Double longitude;
    Double latitude;
    Double oxide;
    Double methane;
    Double ethylene;
    LocalDateTime createTime;
}
