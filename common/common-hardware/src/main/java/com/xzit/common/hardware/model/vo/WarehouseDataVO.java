package com.xzit.common.hardware.model.vo;

import co.elastic.clients.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDataVO {
    Long centerId;
    Double temperature;
    Double humidity;
    Double dioxide;
    Double methane;
    Double oxide;
    Date createTime;
}
