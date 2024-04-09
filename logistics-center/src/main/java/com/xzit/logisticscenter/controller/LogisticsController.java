package com.xzit.logisticscenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.logistics.entity.Area;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.entity.FeeStates;
import com.xzit.common.logistics.model.dto.*;
import com.xzit.common.logistics.model.vo.*;
import com.xzit.common.sys.annotation.AccessLimit;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.logisticscenter.service.*;
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
    private final CourierService courierService;
    private final AddressInfoService addressInfoService;
    private final FeeStatesService feeStatesService;
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
    @PostMapping("/getActiveCourierByQuery")
    @Operation(summary = "分页获取可用快递员")
    ServerResponse<IPage<CourierDTO>> getActiveCourierByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(courierService.getActiveCourierByQuery(queryVO));
    }
    @PostMapping("/getDisableCourierByQuery")
    @Operation(summary = "分页获取不可用快递员")
    ServerResponse<IPage<CourierDTO>> getDisableCourierByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(courierService.getDisableCourierByQuery(queryVO));
    }
    @PutMapping("/addCourier")
    @Operation(summary = "添加快递员")
    ServerResponse<Boolean> addCourier(@RequestBody @Valid CourierVO courierVO){
        if(courierService.addCourier(courierVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.BIND_ERROR);
    }
    @DeleteMapping("/disableCourier/{courierId}")
    @Operation(summary = "禁用快递员")
    ServerResponse<Boolean> disableCourier(@PathVariable Long courierId){
        if(courierService.disableCourier(courierId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @PutMapping("/activeCourier/{courierId}")
    @Operation(summary = "启用快递员")
    ServerResponse<Boolean> activeCourier(@PathVariable Long courierId){
        if(courierService.activeCourier(courierId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @PutMapping("/modifyCourier/{courierId}")
    @Operation(summary = "修改快递员信息")
    ServerResponse<Boolean> modifyCourier(@RequestBody @Valid CourierVO courierVO,@PathVariable Long courierId){
        if(courierService.modifyCourier(courierVO,courierId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @GetMapping("/addressInfo")
    @Operation(summary = "获取个人地址簿")
    ServerResponse<List<AddressInfoDTO>> getAddressInfoList(){
        return ServerResponse.success(addressInfoService.getAddressInfoList());
    }
    @PutMapping("/addAddressInfo")
    @Operation(summary = "添加个人地址")
    ServerResponse<Boolean> addAddressInfo(@RequestBody @Valid AddressInfoVO addressInfoVO){
        if(addressInfoService.addAddressInfo(addressInfoVO)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.BIND_ERROR);
    }
    @DeleteMapping("/deleteAddressInfo/{addressId}")
    @Operation(summary = "删除个人地址")
    ServerResponse<Boolean> deleteAddressInfo(@PathVariable Long addressId){
        if(addressInfoService.deleteAddressInfo(addressId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @PatchMapping("/modifyAddressInfo/{addressId}")
    @Operation(summary = "修改个人地址")
    ServerResponse<Boolean> modifyAddressInfo(@RequestBody @Valid AddressInfoVO addressInfoVO,@PathVariable Long addressId){
        if(addressInfoService.modifyAddressInfo(addressInfoVO,addressId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @PostMapping("/getAddressInfoByQuery")
    @Operation(summary = "分页查询用户地址")
    ServerResponse<IPage<AddressInfoDTO>> getAddressInfoByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(addressInfoService.getUserAddressInfoByQuery(queryVO));
    }
    @GetMapping("/feeCalculateRules")
    @Operation(summary = "获取费用计算规则")
    ServerResponse<List<FeeStatesDTO>> getFeeStates(){
        return ServerResponse.success(feeStatesService.getFeeStates());
    }
    @PatchMapping("/modifyFeeStates")
    @Operation(summary = "修改费用计算规则")
    ServerResponse<?> modifyFeeStates(@RequestBody @Valid FeeStatesVO feeStatesVO){
        feeStatesService.modifyFeeStates(feeStatesVO);
        return ServerResponse.success();
    }
    @PutMapping("/addFeeStates")
    @Operation(summary = "添加费用计算规则")
    ServerResponse<?> addFeeStates(@RequestBody @Valid FeeStatesVO feeStatesVO){
        feeStatesService.addFeeStates(feeStatesVO);
        return ServerResponse.success();
    }
    @PatchMapping("/modifyCenter/{centerId}")
    @Operation(summary = "修改中心")
    ServerResponse<?> modifyCenter(@RequestBody @Valid CenterVO centerVO,@PathVariable Long centerId){
        if(centerService.modifyCenter(centerVO,centerId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }


}