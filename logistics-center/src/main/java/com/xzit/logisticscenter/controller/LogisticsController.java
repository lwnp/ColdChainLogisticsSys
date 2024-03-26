package com.xzit.logisticscenter.controller;

import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.model.vo.CenterVO;
import com.xzit.common.sys.annotation.AccessLimit;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.logisticscenter.service.AreaService;
import com.xzit.logisticscenter.service.CenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tags({@Tag(name = "物流中心模块")})
public class LogisticsController {
    private final AreaService areaService;
    private final CenterService centerService;
    @GetMapping("/areaInfo")
    @Operation(summary = "获取id地区表")
    @AccessLimit(seconds = 600,maxCount = 1)
    ServerResponse<List<Area>> getAreaInfo(){
        return ServerResponse.success(areaService.getAreaInfo());
    }
    @PostMapping("/addLogisticsCenter")
    @Operation(summary = "添加转运中心")
    ServerResponse<Boolean> addLogisticsCenter(@RequestBody @Valid CenterVO centerVO){
        if(centerService.addLogisticsCenter(centerVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.BIND_ERROR);
    }

}
