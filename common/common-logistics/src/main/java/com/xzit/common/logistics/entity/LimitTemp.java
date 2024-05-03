package com.xzit.common.logistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_limit_temp")
@Document(indexName = "limit_temp")
public class LimitTemp {
    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Long)
    @Id
    Long id;
    @Field(type = FieldType.Long)
    Long centerId;
    @Field(type = FieldType.Double)
    Double min;
    @Field(type = FieldType.Double)
    Double max;
    @Field(type = FieldType.Date,format = DateFormat.date_hour_minute_second)
    Date createTime;
    @Field(type = FieldType.Date,format = DateFormat.date_hour_minute_second)
    Date updateTime;
}
