package com.xzit.logisticscenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.model.dto.CarDTO;
import com.xzit.common.logistics.model.dto.CenterDTO;
import com.xzit.common.logistics.model.dto.StationDTO;
import com.xzit.common.logistics.model.vo.CarVO;
import com.xzit.common.logistics.model.vo.CenterVO;
import com.xzit.common.logistics.model.vo.StationVO;
import com.xzit.common.sys.annotation.AccessLimit;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.logisticscenter.service.AreaService;
import com.xzit.logisticscenter.service.CarService;
import com.xzit.logisticscenter.service.CenterService;
import com.xzit.logisticscenter.service.StationService;
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
    private final StationService stationService;
    private final CarService carService;
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
    @Operation(summary = "启用中心")
    ServerResponse<Boolean> activeCenter(@PathVariable Long centerId){
        if(centerService.activeCenter(centerId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @PutMapping("/addLogisticsStation")
    @Operation(summary = "添加配送站")
    ServerResponse<Boolean> addLogisticsStation(@RequestBody @Valid StationVO stationVO){
        if(stationService.addLogisticStation(stationVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.BIND_ERROR);
    }
    @PostMapping("/getActiveStation")
    @Operation(summary = "分页获取配送站")
    ServerResponse<IPage<StationDTO>> getActiveStation(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(stationService.getActiveStation(queryVO));
    }
    @DeleteMapping("/disableStation/{stationId}")
    @Operation(summary = "关闭配送站")
    ServerResponse<Boolean> disableStation(@PathVariable Long stationId){
        if(stationService.disableStation(stationId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @PutMapping("/activeStation/{stationId}")
    @Operation(summary = "开启配送站")
    ServerResponse<Boolean> activeStation(@PathVariable Long stationId){
        if(stationService.activeStation(stationId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @PostMapping("/getDisableStation")
    @Operation(summary = "分页获取不可用配送站")
    ServerResponse<IPage<StationDTO>> getDisableStation(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(stationService.getDisableStation(queryVO));
    }
    @PutMapping("/modifyStation/{stationId}")
    @Operation(summary = "修改配送站信息")
    ServerResponse<Boolean> modifyStation(@RequestBody @Valid StationVO stationVO,@PathVariable Long stationId){
        if(stationService.modifyStation(stationVO,stationId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);

    }
    @PutMapping("/addCar")
    @Operation(summary = "添加冷链车")
    ServerResponse<Boolean> addCar(@RequestBody @Valid CarVO carVO){
        if(carService.addCar(carVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.BIND_ERROR);
    }
    @PostMapping("/getActiveCarByQuery")
    @Operation(summary = "分页获取可用车")
    ServerResponse<IPage<CarDTO>> getActiveCarByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(carService.getActiveCarByQuery(queryVO));
    }
    @PostMapping("/getDisableCarByQuery")
    @Operation(summary = "分页获取不可用车")
    ServerResponse<IPage<CarDTO>> getDisableCarByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(carService.getDisableCarByQuery(queryVO));
    }
    @PutMapping("/modifyCar/{carId}")
    @Operation(summary = "修改车辆信息" )
    ServerResponse<Boolean> modifyCar(@RequestBody @Valid CarVO carVO,@PathVariable Long carId){
        if(carService.modifyCar(carVO,carId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.BIND_ERROR);

    }
    @DeleteMapping("/disableCar/{carId}")
    @Operation(summary = "禁用车辆")
    ServerResponse<Boolean> disableCar(@PathVariable Long carId){
        if(carService.disableCar(carId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @PutMapping("/activeCar/{carId}")
    @Operation(summary = "启用车辆")
    ServerResponse<Boolean> activeCar(@PathVariable Long carId){
        if(carService.activeCar(carId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }




}
