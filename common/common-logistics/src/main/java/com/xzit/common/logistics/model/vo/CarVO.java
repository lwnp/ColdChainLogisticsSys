package com.xzit.common.logistics.model.vo;

import com.xzit.common.logistics.annotation.CarNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "运输车")
public class CarVO {
    @CarNumber
    @Schema(name = "number",description = "车牌号",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    String number;
    @Schema(name = "typeId",description = "类型 1:转运中心 2：配送站",requiredMode = Schema.RequiredMode.AUTO,type = "long")
    Long typeId;
    @Schema(name="logisticId",description = "类型对应的站点id",requiredMode = Schema.RequiredMode.AUTO,type = "long")
    Long logisticId;
    @Schema(name = "maxLoad",description = "最大载重(kg)",requiredMode = Schema.RequiredMode.AUTO,type = "double")
    Double maxLoad;
    @Schema(name = "maxSpace",description = "最大容积(m3)",requiredMode = Schema.RequiredMode.AUTO,type = "double")
    Double maxSpace;
}
