package com.xzit.logisticscenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.model.dto.CenterDTO;
import com.xzit.common.logistics.model.vo.CenterVO;
import com.xzit.common.sys.annotation.AccessLimit;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.logisticscenter.service.AreaService;
import com.xzit.logisticscenter.service.CenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/addLogisticsCenter")
    @Operation(summary = "添加转运中心")
    ServerResponse<Boolean> addLogisticsCenter(@RequestBody @Valid CenterVO centerVO){
        if(centerService.addLogisticsCenter(centerVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.BIND_ERROR);
    }
    @PostMapping("/getAllActiveCenter")
    @Operation(summary = "分页查询可用转运中心")
    ServerResponse<IPage<CenterDTO>> getAllActiveCenter(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(centerService.getActiveCenterByQuery(queryVO));
    }
    @DeleteMapping("/disableCenter/{centerId}")
    @Operation(summary = "关闭中心")
    ServerResponse<Boolean> disableCenter(@PathVariable Long centerId){
        if (centerService.disableCenter(centerId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);

    }
    @PostMapping("/getDisableCenter")
    @Operation(summary = "分页查询不可用转运中心")
    ServerResponse<IPage<CenterDTO>> getDisableCenter(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(centerService.getDisableCenterByQuery(queryVO));
    }
    @GetMapping("/activeCenter/{centerId}")
    ServerResponse<Boolean> activeCenter(@PathVariable Long centerId){

    }

}
