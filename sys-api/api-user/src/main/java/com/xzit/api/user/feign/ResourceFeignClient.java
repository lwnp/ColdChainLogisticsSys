package com.xzit.api.user.feign;

import com.xzit.common.sys.constant.FeignClientAuthorizationConstant;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.user.model.dto.ResourceRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "user-service",contextId = "user-resource")
public interface ResourceFeignClient {
    @GetMapping(FeignClientAuthorizationConstant.AUTHORIZATION_PREFIX+"/user/listResourceRoles")
    ServerResponse<List<ResourceRoleDTO>> listResourceRoles();
}
