package com.xzit.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.api.log.annotation.OptLog;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.enums.ResponseCodeEnum;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.ResourceDTO;
import com.xzit.usercenter.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tags({@Tag(name = "接口相关")})
public class ResourceController {
    private final ResourceService resourceService;
    @PostMapping("/listResourceByQuery")
    @Operation(summary = "获取接口列表",description = "query为空则返回全部")
    ServerResponse<List<ResourceDTO>> listResourceByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(resourceService.listResourceByQuery(queryVO));
    }
    @PostMapping("/listAdminResourceByQuery")
    @Operation(summary = "查询管理员资源")
    ServerResponse<List<ResourceDTO>> listAdminResourceByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(resourceService.listAdminResource(queryVO));

    }
    @GetMapping("/addAdminResource/{resourceId}")
    @OptLog(optType = OptLog.UPDATE)
    @Operation(summary = "添加管理员对资源的访问权")
    ServerResponse<?> addAdminResource(@PathVariable Long resourceId){
        if(resourceService.addAdminResource(resourceId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }
    @DeleteMapping("/dropAdminResource/{resourceId}")
    @OptLog(optType = OptLog.DELETE)
    @Operation(summary = "删除管理员对资源的访问权")
    ServerResponse<?> dropAdminResource(@PathVariable Long resourceId){
        if(resourceService.dropAdminResource(resourceId)){
            return ServerResponse.success();
        }
        return ServerResponse.fail(ResponseCodeEnum.FAIL);
    }


}
