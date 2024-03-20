package com.xzit.logisticscenter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tags({@Tag(name = "物流中心模块")})
public class LogisticsController {
    @GetMapping("/test")
    @Operation(summary = "test")
    String test(){
        return "hello";
    }
}
