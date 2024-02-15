package com.xzit.usercenter.feign;

import com.xzit.api.user.feign.RoleFeignClient;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.usercenter.mapper.RoleMapper;
import com.xzit.usercenter.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoleFeignController implements RoleFeignClient {
    RoleService roleService;

    @Override
    public ServerResponse<String> getRoleNameById(Long id) {
        return ServerResponse.success(roleService.getRoleNameById(id));
    }
}
