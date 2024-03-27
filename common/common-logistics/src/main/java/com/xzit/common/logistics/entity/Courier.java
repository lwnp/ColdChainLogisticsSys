package com.xzit.common.logistics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_logistics_courier")
public class Courier {
    @TableId(type= IdType.AUTO)
    Long id;
    Long userId;
    Long typeId;
    Long logisticId;
}
