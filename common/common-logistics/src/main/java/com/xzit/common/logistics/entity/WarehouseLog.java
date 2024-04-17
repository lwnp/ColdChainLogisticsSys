package com.xzit.common.logistics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_warehouse_log")
public class WarehouseLog {
    @TableId(type = IdType.AUTO)
    Long id;
    Long centerId;
    String orderNum;
    Long preStepId;
    Long nextStepId;
    Long userInfoId;
    Boolean isStored;
    LocalDateTime inTime;
    LocalDateTime outTime;
}
