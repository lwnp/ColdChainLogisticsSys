package com.xzit.common.user.model.vo;

import com.xzit.common.user.annotation.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息")
public class UserInfoVO {
    @Schema(name = "nickname",description = "昵称",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    @Size(max =16,message = "昵称不得多于16个字符")
    String nickname;
    @Schema(name = "avatar",description = "头像url",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    String avatar;
    @Phone
    @Size(max = 11,message = "手机号11位")
    @Schema(name = "phone",description = "手机号",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    String phone;

}
