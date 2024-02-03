package com.xzit.api.user.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user-service",contextId = "user")
public interface UserFeignClient {

}
