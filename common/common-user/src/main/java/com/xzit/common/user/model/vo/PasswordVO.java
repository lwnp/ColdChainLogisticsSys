package com.xzit.common.user.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "密码重置")
public class PasswordVO {
    @NotBlank(message = "用户名不能为空")
    @Size(max = 16,message = "用户名必须小于16位")
    @Schema(name = "username", description = "用户名",requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    String username;
    @Max(value = 16,message = "长度不能超过16")
    @Schema(name = "password",description = "密码",requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    String password;
    @Schema(name = "code",description = "验证码",requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    String code;


}
