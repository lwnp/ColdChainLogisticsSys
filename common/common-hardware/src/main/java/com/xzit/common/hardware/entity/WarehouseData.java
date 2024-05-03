package com.xzit.common.hardware.entity;

import co.elastic.clients.util.DateTime;
import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("t_warehouse_data")
@Document(indexName = "warehouse_data")
public class WarehouseData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Long)
    @Id
    Long id;
    @Field(type = FieldType.Long)
    @JSONField(name = "center_id")
    Long centerId;
    @Field(type = FieldType.Double)
    Double temperature;
    @Field(type = FieldType.Double)
    Double humidity;
    @Field(type = FieldType.Double)
    Double dioxide;
    @Field(type = FieldType.Double)
    Double oxide;
    @Field(type = FieldType.Double)
    Double methane;
    @Field(type = FieldType.Date,format = DateFormat.date_hour_minute_second)
    @JSONField(name = "create_time")
    Date createTime;
}
