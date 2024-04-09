package com.xzit.common.logistics.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "费用阶梯")
public class FeeStatesVO {
    @Schema(name = "id", description = "id",requiredMode = Schema.RequiredMode.AUTO,type = "long")
    Long id;
    @Schema(name = "feeTypeId", description = "何种阶梯1 起步价(只能修改时用,因为只能有一个起步价) 2 距离 3 重量 4 体积",requiredMode = Schema.RequiredMode.AUTO,type = "long")
    Long feeTypeId;
    @Schema(name = "limit", description = "阶梯的下限",requiredMode = Schema.RequiredMode.AUTO,type = "double")
    Double limit;
    @Schema(name = "price", description = "阶梯的价格",requiredMode = Schema.RequiredMode.AUTO,type = "double")
    BigDecimal price;
    @Schema(name = "oilRate", description = "燃油费率",requiredMode = Schema.RequiredMode.AUTO,type = "double")
    Double oilRate;
    @Schema(name = "state", description = "某种阶梯的第几级",requiredMode = Schema.RequiredMode.AUTO,type = "long")
    Long state;
}
