package com.xzit.messagecenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.sys.model.dto.NoticeDTO;
import com.xzit.common.sys.model.vo.QueryVO;

public interface NoticeService {
    IPage<NoticeDTO> getUserNoticeByQuery(QueryVO queryVO);
    IPage<NoticeDTO> getUserUnReadNoticeByQuery(QueryVO queryVO);
    IPage<NoticeDTO> getAdminNoticeByQuery(QueryVO queryVO);
    IPage<NoticeDTO> getAdminUnReadNoticeByQuery(QueryVO queryVO);
    void dismissUserNotice(Long noticeId);
    void dismissAdminNotice(Long noticeId);
    void deleteUserNotice(Long noticeId);
    void deleteAdminNotice(Long noticeId);
}
