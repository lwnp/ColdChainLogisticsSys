package com.xzit.common.sys.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @PositiveOrZero
    @NotEmpty
    @Schema(name = "pageNum",description = "页码",requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    Integer pageNum;
    @NotEmpty
    @Positive
    @Schema(name = "pageSize",description = "每页数目",requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    Integer pageSize;

    String query;

}
