package com.xzit.usercenter.feign;

import com.xzit.api.user.feign.RoleFeignClient;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.usercenter.service.RoleService;
import com.xzit.usercenter.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleFeignController implements RoleFeignClient {
    private final RoleService roleService;
    private final UserInfoService  userInfoService;

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

    @Override
    public ServerResponse<Boolean> isValidCourier(Long userInfoId) {
        return ServerResponse.success(userInfoService.isValidCourier(userInfoId));
    }
}
