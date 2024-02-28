package com.xzit.common.user.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户账号")
public class UserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(name = "username", description = "用户名",requiredMode = Schema.RequiredMode.REQUIRED,type = "String")
    private String username;
    @Size(min = 6, message = "密码不能少于6位")
    @NotBlank(message = "密码不能为空")
    @Schema(name = "password", description = "密码", requiredMode = Schema.RequiredMode.REQUIRED,type = "String")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Schema(name = "code",description = "验证码",requiredMode = Schema.RequiredMode.REQUIRED,type = "String")
    private String code;
}
