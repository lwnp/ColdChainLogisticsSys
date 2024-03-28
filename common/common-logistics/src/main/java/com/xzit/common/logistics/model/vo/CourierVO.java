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
@Schema(description = "配送员(司机)")
public class CourierVO {
    @Schema(name = "userInfoId",description = "用户信息id",type = "long",requiredMode = Schema.RequiredMode.AUTO)
    Long userInfoId;
    @Schema(name = "typeId",description = "类型: 1 转运中心 2 配送站",type = "long",requiredMode = Schema.RequiredMode.AUTO)
    Long typeId;
    @Schema(name = "logisticId",description = "对应站点id",type = "long",requiredMode = Schema.RequiredMode.AUTO)
    Long logisticId;
}
