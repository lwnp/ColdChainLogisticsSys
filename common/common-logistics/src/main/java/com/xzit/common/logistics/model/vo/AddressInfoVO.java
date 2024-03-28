package com.xzit.common.logistics.model.vo;

import com.xzit.common.user.annotation.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "地址信息")
public class AddressInfoVO {
    @Schema(name = "areaId" ,description = "地区id 参见地区id表",requiredMode = Schema.RequiredMode.AUTO,type = "long")
    Long areaId;
    @Schema(name = "userInfoId" ,description = "用户信息id",requiredMode = Schema.RequiredMode.AUTO,type = "long")
    Long userInfoId;
    @Schema(name = "name" ,description = "收/发件姓名",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    String name;
    @Phone
    @Schema(name = "phone" ,description = "收/发件电话",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    String phone;
    @Schema(name = "address" ,description = "收/发件地址",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    String address;
}
