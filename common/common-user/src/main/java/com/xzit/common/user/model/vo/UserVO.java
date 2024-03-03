package com.xzit.common.user.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotBlank(message = "用户名不能为空")
    @Size(max = 16,message = "用户名必须小于16位")
    @Schema(name = "username", description = "用户名",requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    String username;
    @Size(min = 6,max = 16,message = "密码不能少于6位且必须小于16位")
    @NotBlank(message = "密码不能为空")
    @Schema(name = "password", description = "密码", requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    String password;
    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    @Schema(name = "email",description = "邮箱",requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    String email;
    @NotBlank(message = "验证码不能为空")
    @Schema(name = "code",description = "验证码",requiredMode = Schema.RequiredMode.REQUIRED,type = "string")
    String code;
}
