package com.xzit.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.ResourceDTO;
import com.xzit.usercenter.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tags({@Tag(name = "接口相关")})
public class ResourceController {
    private final ResourceService resourceService;
    @PostMapping("/listResourceByQuery")
    @Operation(summary = "获取接口列表",description = "query为空则返回全部")
    ServerResponse<IPage<ResourceDTO>> listResourceByQuery(@RequestBody @Valid QueryVO queryVO){
        return ServerResponse.success(resourceService.listResourceByQuery(queryVO));
    }

}
