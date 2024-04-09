package com.xzit.messagecenter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.sys.entity.Notice;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.sys.model.dto.NoticeDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.messagecenter.mapper.NoticeMapper;
import com.xzit.messagecenter.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final UserFeignClient userFeignClient;
    private final NoticeMapper noticeMapper;

    private UserInfoDTO getCurrentUserInfo() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = jwt.getClaims();
        Long userId = (Long) map.get("userId");
        return userFeignClient.getUserInfo(userId).getData();
    }

    @Override
    public IPage<NoticeDTO> getUserNoticeByQuery(QueryVO queryVO) {
        UserInfoDTO userInfoDTO = getCurrentUserInfo();
        Page<NoticeDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return noticeMapper.getUserMessageByQuery(page, queryVO, userInfoDTO.getId());
    }

    @Override
    public IPage<NoticeDTO> getUserUnReadNoticeByQuery(QueryVO queryVO) {
        UserInfoDTO userInfoDTO = getCurrentUserInfo();
        Page<NoticeDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return noticeMapper.getUnreadMessageByQuery(page, queryVO, userInfoDTO.getId());
    }

    @Override
    public IPage<NoticeDTO> getAdminNoticeByQuery(QueryVO queryVO) {
        Page<NoticeDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return noticeMapper.getAllAdminMessageByQuery(page, queryVO);
    }

    @Override
    public IPage<NoticeDTO> getAdminUnReadNoticeByQuery(QueryVO queryVO) {
        Page<NoticeDTO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return noticeMapper.getUnreadAdminMessageByQuery(page, queryVO);
    }

    @Override
    public void dismissUserNotice(Long noticeId) {
        UserInfoDTO userInfoDTO = getCurrentUserInfo();
        if (!Objects.equals(userInfoDTO.getId(), noticeId)) {
            throw new RuntimeException("无法修改他人的消息");
        }
        Notice notice = noticeMapper.selectById(noticeId);
        if (notice == null) {
            throw new BizException("消息不存在");
        }
        notice.setIsRead(true);
        noticeMapper.updateById(notice);
    }

    @Override
    public void dismissAdminNotice(Long noticeId) {
        Notice notice = noticeMapper.selectById(noticeId);
        if (notice == null || !notice.getIsAdminMessage()) {
            throw new BizException("消息不存在或非管理员消息");
        }
        notice.setIsRead(true);
        noticeMapper.updateById(notice);
    }

    @Override
    public void deleteUserNotice(Long noticeId) {
        UserInfoDTO userInfoDTO = getCurrentUserInfo();
        if (!Objects.equals(userInfoDTO.getId(), noticeId)) {
            throw new RuntimeException("无法修改他人的消息");
        }
        Notice notice = noticeMapper.selectById(noticeId);
        if (notice == null) {
            throw new BizException("消息不存在");
        }
        noticeMapper.deleteById(noticeId);
    }

    @Override
    public void deleteAdminNotice(Long noticeId) {
        Notice notice = noticeMapper.selectById(noticeId);
        if (notice == null || !notice.getIsAdminMessage()) {
            throw new BizException("消息不存在或非管理员消息");
        }
        noticeMapper.deleteById(noticeId);
    }
}
