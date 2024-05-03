package com.xzit.common.logistics.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "温度报警阈值")
public class LimitTempVO {
    @Schema(name = "id", description = "id",requiredMode = Schema.RequiredMode.REQUIRED,type = "long")
    Long id;
    @Schema(name ="max",description = "最大温度",requiredMode = Schema.RequiredMode.REQUIRED,type = "double")
    Double max;
    @Schema(name ="min",description = "最小温度",requiredMode = Schema.RequiredMode.REQUIRED,type = "double")
    Double min;

}
