package com.xzit.usercenter.feign;

import com.xzit.api.user.feign.ResourceFeignClient;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.user.model.dto.ResourceRoleDTO;
import com.xzit.usercenter.service.ResourceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResourceFeignController implements ResourceFeignClient {
    private final ResourceService resourceService;
    @Override
    public ServerResponse<List<ResourceRoleDTO>> listResourceRoles() {
        List<ResourceRoleDTO> resourceRoles=resourceService.listResourceRoles();
        if (resourceRoles.isEmpty()){
            return ServerResponse.fail(ResponseCodeEnum.DATABASE_ERROR.getCode(),ResponseCodeEnum.DATABASE_ERROR.getDesc());
        }
        return ServerResponse.success(resourceService.listResourceRoles());
    }
}
