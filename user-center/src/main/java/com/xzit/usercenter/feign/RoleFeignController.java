package com.xzit.usercenter.feign;

import com.xzit.api.user.feign.RoleFeignClient;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.usercenter.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoleFeignController implements RoleFeignClient {
    RoleService roleService;

    @Override
    public ServerResponse<String> getRoleNameById(Long id) {
        return ServerResponse.success(roleService.getRoleNameById(id));
    }

    @Override
    public ServerResponse<List<String>> listUserRoles(String username) {
        List<String> userRoles=roleService.listUserRoles(username);
        if(userRoles.isEmpty()){
            return ServerResponse.fail(ResponseCodeEnum.DATABASE_ERROR.getCode(),ResponseCodeEnum.DATABASE_ERROR.getDesc());
        }
        return ServerResponse.success(userRoles);
    }
}
