package com.xzit.common.logistics.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@Data
@TableName("t_station_car")
public class StationCar extends Car{
    public  StationCar(){
        super();
    }
    @TableField("station_id")
    Long stationId;
}
