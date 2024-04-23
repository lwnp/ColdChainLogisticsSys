package com.xzit.logisticscenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.file.service.FileService;
import com.xzit.common.hardware.model.dto.WarehouseDataDTO;
import com.xzit.common.logistics.constant.LogisticConstant;
import com.xzit.common.logistics.entity.Area;
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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private final LogisticService logisticService;
    private final FileService fileService;
    private final LogisticFlowService logisticFlowService;
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
    @PostMapping("/getArrangementByQuery")
    @Operation(summary = "管理员分页查询物流任务")
    ServerResponse<IPage<ArrangementDTO>> getArrangementByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(logisticService.getArrangementByQuery(queryVO));
    }
    @GetMapping("/getCourierArrangement")
    @Operation(summary = "获取快递员自己当前任务")
    ServerResponse<List<ArrangementDTO>> getCourierArrangement(){
        return ServerResponse.success(logisticService.getCourierArrangement());
    }
    @PostMapping("/getCourierHistoryArrangement")
    @Operation(summary = "快递员分页查询历史任务")
    ServerResponse<IPage<ArrangementDTO>> getCourierHistoryArrangement(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(logisticService.getHistoryArrangementByQuery(queryVO));
    }
    @GetMapping("/startShipping/{orderNum}")
    @Operation(summary = "开始物流")
    ServerResponse<?> startShipping(@PathVariable String orderNum){
        logisticService.startShipping(orderNum);
        return ServerResponse.success();
    }
    @GetMapping("/getUserAddress/{orderNum}")
    @Operation(summary = "司机获取用户地址")
    ServerResponse<AddressInfoDTO> getUserAddress(@PathVariable String orderNum){
        return ServerResponse.success(logisticService.courierGetUserAddress(orderNum));
    }
    @PutMapping("/pickUpConfirm")
    @Operation(summary = "确认揽件")
    ServerResponse<?> pickUpConfirm(@RequestBody @Valid LogisticFlowVO logisticFlowVO){
        logisticService.pickUpConfirm(logisticFlowVO);
        return ServerResponse.success();
    }
    @PostMapping("/logisticImageUpload")
    @Operation(summary = "物流图片上传")
    ServerResponse<String> logisticImageUpload(@RequestBody MultipartFile file){
        return ServerResponse.success(fileService.uploadFile(file, LogisticConstant.IMAGE_SEPARATOR));
    }

    @PostMapping("/senderStationArriveConfirm")
    @Operation(summary = "发件站到达确认")
    ServerResponse<?> senderStationArriveConfirm(@RequestBody @Valid LogisticFlowVO logisticFlowVO) {
        logisticService.senderStationArriveConfirm(logisticFlowVO);
        return ServerResponse.success();
    }
    @PostMapping("/senderStationReleaseConfirm")
    @Operation(summary = "发件站释放确认")
    ServerResponse<?> senderStationReleaseConfirm(@RequestBody @Valid LogisticFlowVO logisticFlowVO) {
        logisticService.senderStationReleaseConfirm(logisticFlowVO);
        return ServerResponse.success();
    }
    @PostMapping("/receiveStationArriveConfirm")
    @Operation(summary = "收件站到达确认")
    ServerResponse<?> receiveStationArriveConfirm(@RequestBody @Valid LogisticFlowVO logisticFlowVO) {
        logisticService.receiveStationArriveConfirm(logisticFlowVO);
        return ServerResponse.success();
    }
    @PostMapping("/receiveStationReleaseConfirm")
    @Operation(summary = "收件站释放确认")
    ServerResponse<?> receiveStationReleaseConfirm(@RequestBody @Valid LogisticFlowVO logisticFlowVO) {
        logisticService.receiveStationReleaseConfirm(logisticFlowVO);
        return ServerResponse.success();
    }
    @PostMapping("/receiveConfirm")
    @Operation(summary = "收件确认")
    ServerResponse<?> receiveConfirm(@RequestBody @Valid LogisticFlowVO logisticFlowVO) {
        logisticService.receiveConfirm(logisticFlowVO);
        return ServerResponse.success();
    }
    @PostMapping("/senderCenterArriveConfirmAndStored")
    @Operation(summary = "发件中心到达并入库确认")
    ServerResponse<?> senderCenterArriveConfirmAndStored(@RequestBody @Valid LogisticFlowVO logisticFlowVO) {
        logisticService.senderCenterArriveConfirmAndStored(logisticFlowVO);
        return ServerResponse.success();
    }
    @PostMapping("/receiveCenterArriveConfirmAndStored")
    @Operation(summary = "收件中心到达并入库确认")
    ServerResponse<?> receiveCenterArriveConfirmAndStored(@RequestBody @Valid LogisticFlowVO logisticFlowVO) {
        logisticService.receiveCenterArriveConfirmAndStored(logisticFlowVO);
        return ServerResponse.success();
    }
    @PostMapping("/senderCenterDropAndReleaseConfirm")
    @Operation(summary = "发件中心出库并释放确认")
    ServerResponse<?> senderCenterDropAndReleaseConfirm(@RequestBody @Valid LogisticFlowVO logisticFlowVO) {
        logisticService.senderCenterDropAndReleaseConfirm(logisticFlowVO);
        return ServerResponse.success();
    }
    @PostMapping("/receiveCenterDropAndReleaseConfirm")
    @Operation(summary = "收件中心出库并释放确认")
    ServerResponse<?> receiveCenterDropAndReleaseConfirm(@RequestBody @Valid LogisticFlowVO logisticFlowVO) {
        logisticService.receiveCenterDropAndReleaseConfirm(logisticFlowVO);
        return ServerResponse.success();
    }
    @GetMapping("/releaseArrange/{userInfoId}/{orderNum}")
    @Operation(summary = "释放物流任务占用")
    ServerResponse<?> releaseArrange(@PathVariable Long userInfoId,@PathVariable String orderNum){
        logisticService.releaseArrange(userInfoId,orderNum);
        return ServerResponse.success();
    }
    @GetMapping("/getSimpleLogisticFlowByOrderNum/{orderNum}")
    @Operation(summary = "用户获取物流进程")
    ServerResponse<List<SimpleLogisticFlowDTO>> getSimpleLogisticFlowByOrderNum(@PathVariable String orderNum){
        return ServerResponse.success(logisticFlowService.getSimpleLogisticFlowByOrderNum(orderNum));
    }
    @GetMapping("/getLogisticFlowByOrderNum/{orderNum}")
    @Operation(summary = "管理员获取物流进程")
    ServerResponse<List<LogisticFlowDTO>> getLogisticFlowByOrderNum(@PathVariable String orderNum){
        return ServerResponse.success(logisticFlowService.getLogisticFlowByOrderNum(orderNum));
    }
    @GetMapping("/getLocationFile/{orderNum}")
    @Operation(summary = "获取物流定位文件")
    void getLocationFile(@PathVariable String orderNum,HttpServletResponse response) throws IOException {
        InputStream is = new ByteArrayInputStream(logisticFlowService.getLocationFile(orderNum));
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+orderNum+".json");
        OutputStream os =response.getOutputStream();
        IOUtils.copy(is,os);
        os.flush();
        os.close();
        is.close();
    }
    @GetMapping("/getWarehouseData/{orderNum}")
    @Operation(summary = "获取仓库监测数据")
    ServerResponse<List<WarehouseDataDTO>> getWarehouseData(@PathVariable String orderNum){
        return ServerResponse.success(logisticFlowService.getWarehouseData(orderNum));
    }
    @GetMapping("/getWarehouseLiveStreamUrl/{orderNum}")
    @Operation(summary = "获取仓库实时视频流" )
    ServerResponse<String> getLiveStreamUrl(@PathVariable String orderNum){
        return ServerResponse.success(logisticFlowService.getWarehouseLiveStreamUrl(orderNum));
    }
    @GetMapping("/getCarLiveStreamUrl/{orderNum}")
    @Operation(summary = "获取车辆实时视频流" )
    ServerResponse<List<String>> getCarLiveStreamUrl(@PathVariable String orderNum){
        return ServerResponse.success(logisticFlowService.getCarLiveStreamUrl(orderNum));
    }

}