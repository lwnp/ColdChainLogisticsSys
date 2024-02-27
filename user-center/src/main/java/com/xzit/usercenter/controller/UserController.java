package com.xzit.usercenter.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Operation(summary = "测试")
    @GetMapping("/user/test")
    String test(){
        return "hello";
    }
}
