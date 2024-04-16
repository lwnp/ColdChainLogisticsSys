package com.xzit.ordercenter.controller;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.order.model.dto.GoodsDTO;
import com.xzit.common.order.model.dto.OrderDTO;
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

@RestController
@RequiredArgsConstructor
@Tags({@Tag(name = "订单模块接口")})
public class OrderController {
    private final GoodsService goodsService;
    private final OrderService orderService;
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

}
