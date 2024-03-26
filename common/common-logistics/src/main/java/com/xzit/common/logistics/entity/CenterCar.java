package com.xzit.common.logistics.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@Data
@TableName("t_center_car")
public class CenterCar extends Car{
    public CenterCar(){
        super();
    }
    @TableField(value = "center_id")
    Long centerId;
}
