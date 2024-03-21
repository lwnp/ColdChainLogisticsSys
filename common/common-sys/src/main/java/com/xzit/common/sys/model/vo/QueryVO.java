package com.xzit.common.sys.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "查询请求")
public class QueryVO {
    @Min(value = 1,message = "最小页码为1")
    @Schema(name = "pageNum",description = "页码",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    Integer pageNum;
    @Schema(name = "pageSize",description = "每页数目",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    @Min(value = 1,message = "最小数目为1")
    Integer pageSize;
    String query;

}
