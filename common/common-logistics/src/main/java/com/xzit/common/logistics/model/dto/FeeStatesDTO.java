package com.xzit.common.logistics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeeStatesDTO {
    Long id;
    String name;
    Double startValue;
    BigDecimal price;
    Double oilRate;
    Long state;
    LocalDateTime createTime;
    LocalDateTime updateTime;

}
