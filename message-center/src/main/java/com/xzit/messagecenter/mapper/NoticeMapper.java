package com.xzit.messagecenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.sys.entity.Notice;
import com.xzit.common.sys.model.dto.NoticeDTO;
import com.xzit.common.sys.model.vo.QueryVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    IPage<NoticeDTO> getUserMessageByQuery(Page<NoticeDTO> page, QueryVO queryVO);
}
