package com.xzit.logcenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.log.entity.ExceptionLog;
import com.xzit.common.log.model.dto.ExceptionLogDTO;
import com.xzit.common.log.model.dto.OperationLogDTO;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.logcenter.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tags({@Tag(name ="日志接口")})
public class LogController {
    private final LogService logService;
    @PostMapping("/getOperationLogByQuery")
    @Operation(summary = "分页获取操作日志")
    ServerResponse<IPage<OperationLogDTO>> getOperationLogByQuery(@RequestBody @Valid QueryVO queryVO) {
        return ServerResponse.success(logService.getOperationLogByQuery(queryVO));
    }
    @PostMapping("/getExceptionLogByQuery")
    @Operation(summary = "分页获取异常日志")
    ServerResponse<IPage<ExceptionLogDTO>> getExceptionLogByQuery(@RequestBody @Valid QueryVO queryVO) {
        return ServerResponse.success(logService.getExceptionLogByQuery(queryVO));
    }
    @DeleteMapping("/deleteOperationLog/{id}")
    @Operation(summary = "删除操作日志")
    ServerResponse<?> deleteOperationLog(@PathVariable("id") Long id) {
        logService.deleteOperationLog(id);
        return ServerResponse.success();
    }
    @DeleteMapping("/deleteExceptionLog/{id}")
    @Operation(summary = "删除异常日志")
    ServerResponse<?> deleteExceptionLog(@PathVariable("id") Long id) {
        logService.deleteExceptionLog(id);
        return ServerResponse.success();
    }
}
