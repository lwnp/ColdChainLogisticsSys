package com.xzit.ordercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.file.service.FileService;
import com.xzit.common.order.constant.OrderConstant;
import com.xzit.common.order.model.dto.*;
import com.xzit.common.order.model.vo.GoodsVO;
import com.xzit.common.order.model.vo.OrderVO;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.ordercenter.service.GoodsService;
import com.xzit.ordercenter.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tags({@Tag(name = "订单模块接口")})
public class OrderController {
    private final GoodsService goodsService;
    private final OrderService orderService;
    private final FileService fileService;
    @PutMapping("/addGoods")
    @Operation(summary = "添加货物")
    ServerResponse<?> addGoods(@RequestBody @Valid GoodsVO goodsVO){
        goodsService.addGoods(goodsVO);
        return ServerResponse.success();
    }
    @PatchMapping("/modifyGoods/{goodsId}")
    @Operation(summary = "修改货物")
    ServerResponse<?> modifyGoods(@RequestBody @Valid GoodsVO goodsVO,@PathVariable Long goodsId){
        goodsService.modifyGoods(goodsVO,goodsId);
        return ServerResponse.success();
    }
    @PostMapping("/getUserGoods")
    @Operation(summary = "分页获取用户自己货物")
    ServerResponse<IPage<GoodsDTO>> getUserGoods(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(goodsService.getUserGoodsByQuery(queryVO));
    }
    @PostMapping("/getAllGoods")
    @Operation(summary = "管理员分页获取所有货物")
    ServerResponse<IPage<GoodsDTO>> getAllGoods(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(goodsService.getAllGoodsByQuery(queryVO));
    }
    @PostMapping("/getUncheckedGoods")
    @Operation(summary = "管理员分页获取未审核货物")
    ServerResponse<IPage<GoodsDTO>> getUncheckedGoods(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(goodsService.getUncheckedGoodsByQuery(queryVO));
    }
    @GetMapping("/approveGoods/{goodsId}")
    @Operation(summary = "管理员审核接受货物")
    ServerResponse<?> approveGoods(@PathVariable Long goodsId){
        goodsService.approveGoods(goodsId);
        return ServerResponse.success();
    }
    @GetMapping("/rejectGoods/{goodsId}")
    @Operation(summary = "管理员审核拒绝货物")
    ServerResponse<?> rejectGoods(@PathVariable Long goodsId){
        goodsService.rejectGoods(goodsId);
        return ServerResponse.success();
    }
    @DeleteMapping("/deleteGoods/{goodsId}")
    @Operation(summary = "用户删除货物")
    ServerResponse<?> deleteGoods(@PathVariable Long goodsId){
        goodsService.deleteGoods(goodsId);
        return ServerResponse.success();
    }
    @PostMapping("/generateAlipayUrl")
    @Operation(summary = "下单生成支付宝支付链接")
    ServerResponse<String> generateAlipayUrl(@RequestBody @Valid OrderVO orderVO) {
        return ServerResponse.success(orderService.generateAlipayUrl(orderVO));
    }
    @PostMapping("/getOrderByQuery")
    @Operation(summary = "管理员分页获取所有订单")
    ServerResponse<IPage<OrderDTO>> getOrderByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(orderService.getOrderByQuery(queryVO));
    }
    @PostMapping("/getActiveOderByQuery")
    @Operation(summary = "管理员分页获取所有进行中订单")
    ServerResponse<IPage<OrderDTO>> getActiveOderByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(orderService.getActiveOderByQuery(queryVO));
    }
    @GetMapping("/getUnpaidOrder")
    @Operation(summary = "获取未支付订单")
    ServerResponse<OrderDTO> getUnpaidOrder(){
        return ServerResponse.success(orderService.getUnpaidOrder());
    }
    @PostMapping("/uploadGoodsImage")
    @Operation(summary = "上传货物图片")
    ServerResponse<String> uploadGoodsImage(@RequestBody MultipartFile file){
        return ServerResponse.success(fileService.uploadFile(file, OrderConstant.ORDER_SEPARATOR));
    }
    @PostMapping("/getUserOrderByQuery")
    @Operation(summary = "用户分页获取所有订单")
    ServerResponse<IPage<OrderDTO>> getUserOrderByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(orderService.getUserOrderByQuery(queryVO));
    }
    @PostMapping("/getUserActiveOrderByQuery")
    @Operation(summary = "用户分页获取所有进行中订单")
    ServerResponse<IPage<OrderDTO>> getUserActiveOrderByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(orderService.getUserActiveOrderByQuery(queryVO));
    }
    @PostMapping("/getReceiverOrderByQuery")
    @Operation(summary = "收件人分页获取进行中收货订单")
    ServerResponse<IPage<OrderDTO>> getReceiverOrderByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(orderService.getReceiverOrderByQuery(queryVO));
    }
    @PostMapping("/getReceiverHistoryOrderByQuery")
    @Operation(summary = "收件人分页获取所有历史收货订单")
    ServerResponse<IPage<OrderDTO>> getReceiverHistoryOrderByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(orderService.getReceiverHistoryOrderByQuery(queryVO));
    }
    @DeleteMapping("/deleteOrder/{orderId}")
    @Operation(summary = "用户删除订单")
    ServerResponse<IPage<OrderDTO>> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ServerResponse.success();
    }
    @GetMapping("/continuePay/{orderNum}")
    @Operation(summary = "继续支付",description = "若在订单创建后未立即支付,可通过该接口重新获取支付链接,这个并非重新生成的，时效5min是从订单创建开始的")
    ServerResponse<String> continuePay(@PathVariable String orderNum){
        return ServerResponse.success(orderService.continuePay(orderNum));
    }
    @PostMapping("/getAvailableGoods")
    @Operation(summary = "用户分页获取已审核货物")
    ServerResponse<IPage<GoodsDTO>> getAvailableGoods(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(goodsService.getAvailableGoodsByQuery(queryVO));
    }
    @GetMapping("/getOrderStaticsData")
    @Operation(summary = "获取12个月订单付款统计数据")
    ServerResponse<List<OrderStaticsDataDTO>> getOrderStaticsData(){
        return ServerResponse.success(orderService.getOrderStaticsData());
    }
    @GetMapping("/getPaymentStaticsData")
    @Operation(summary = "总成交量等数据")
    ServerResponse<PaymentStaticsDataDTO> getPaymentStaticsData(){
        return ServerResponse.success(orderService.getPaymentStaticsData());
    }
    @GetMapping("/getAreaStaticsData/{areaId}")
    @Operation(summary = "获取区域订单统计数据")
    ServerResponse<List<AreaStaticsDataDTO>> getAreaStaticsData(@PathVariable Long areaId){
        return ServerResponse.success(orderService.getAreaStaticsData(areaId));
    }

}
