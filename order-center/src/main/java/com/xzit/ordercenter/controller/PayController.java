package com.xzit.ordercenter.controller;

import com.xzit.common.order.model.vo.PayResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {
    @GetMapping("/redirect")
    public String redirect(){
        return "test";
    }
    @PostMapping("/notify")
    public void tnotify(PayResultVO payResultVO){
        System.out.println(payResultVO);
    }




}
