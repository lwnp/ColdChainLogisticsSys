package com.xzit.common.logistics.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "配送站")
public class StationVO {
    @Schema(name = "areaId",description = "省份id",requiredMode = Schema.RequiredMode.AUTO,type = "long")
    Long areaId;
    @Schema(name = "name",description = "配送站名",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    String name;
    @Schema(name = "address",description = "地址",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    String address;
    @Schema(name = "longitude",description = "经度",requiredMode = Schema.RequiredMode.AUTO,type = "double")
    Double longitude;
    @Schema(name = "latitude",description = "纬度",requiredMode = Schema.RequiredMode.AUTO,type = "double")
    Double latitude;

}
