package com.xzit.common.sys.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "更换邮箱")
public class EmailVO {
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(name = "email",description = "邮箱",requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    String email;
    @Schema(name = "code",description = "验证码",requiredMode = Schema.RequiredMode.AUTO,type = "string")
    String code;
}
