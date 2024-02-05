package com.xzit.usercenter.controller;

import com.xzit.common.sys.entity.ServerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/test")
    String test(){
        return "hello";
    }
}
