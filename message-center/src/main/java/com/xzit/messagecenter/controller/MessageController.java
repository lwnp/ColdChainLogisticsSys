package com.xzit.messagecenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.api.log.annotation.OptLog;
import com.xzit.common.sys.entity.ServerResponse;
import com.xzit.common.sys.model.dto.NoticeDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.messagecenter.mapper.NoticeMapper;
import com.xzit.messagecenter.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tags({@Tag(name = "消息接口")})
public class MessageController {
    private final NoticeService noticeService;
    @PostMapping("/getUserNoticeByQuery")
    @Operation(summary = "分页获取用户自己消息")
    ServerResponse<IPage<NoticeDTO>> getUserNoticeByQuery(QueryVO queryVO){
        return ServerResponse.success(noticeService.getUserNoticeByQuery(queryVO));
    }
    @PostMapping("/getUserUnreadNoticeByQuery")
    @Operation(summary = "分页获取用户未读消息")
    ServerResponse<IPage<NoticeDTO>> getUserUnreadNoticeByQuery(QueryVO queryVO){
        return ServerResponse.success(noticeService.getUserUnReadNoticeByQuery(queryVO));
    }
    @PostMapping("/getAdminNoticeByQuery")
    @Operation(summary = "分页获取管理员消息")
    ServerResponse<IPage<NoticeDTO>> getAdminNoticeByQuery(QueryVO queryVO){
        return ServerResponse.success(noticeService.getAdminNoticeByQuery(queryVO));
    }
    @PostMapping("/getAdminUnreadNoticeByQuery")
    @Operation(summary = "分页获取管理员未读消息")
    ServerResponse<IPage<NoticeDTO>> getAdminUnreadNoticeByQuery(QueryVO queryVO){
        return ServerResponse.success(noticeService.getAdminUnReadNoticeByQuery(queryVO));
    }
    @DeleteMapping("/deleteUserNotice/{noticeId}")
    @Operation(summary = "删除用户消息")
    ServerResponse<?> deleteUserNotice(@PathVariable Long noticeId){
        noticeService.deleteUserNotice(noticeId);
        return ServerResponse.success();
    }
    @DeleteMapping("/deleteAdminNotice/{noticeId}")
    @Operation(summary = "删除管理员消息")
    @OptLog(optType = OptLog.DELETE)
    ServerResponse<?> deleteAdminNotice(@PathVariable Long noticeId){
        noticeService.deleteAdminNotice(noticeId);
        return ServerResponse.success();
    }
    @PatchMapping("/dismissUserNotice/{noticeId}")
    @Operation(summary = "已读用户消息")
    ServerResponse<?> dismissUserNotice(@PathVariable Long noticeId){
        noticeService.dismissUserNotice(noticeId);
        return ServerResponse.success();

    }
    @PatchMapping("/dismissAdminNotice/{noticeId}")
    @Operation(summary = "已读管理员消息")
    @OptLog(optType = OptLog.DELETE)
    ServerResponse<?> dismissAdminNotice(@PathVariable Long noticeId){
        noticeService.dismissAdminNotice(noticeId);
        return ServerResponse.success();
    }

}
