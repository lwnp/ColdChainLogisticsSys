package com.xzit.usercenter.controller.feign;

import com.xzit.api.user.feign.UserFeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFeignController implements UserFeignClient {
}
