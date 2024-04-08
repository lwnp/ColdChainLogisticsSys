package com.xzit.messagecenter.consumer;

import com.xzit.common.sys.entity.Notice;
import com.xzit.messagecenter.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class NoticeConsumer {
    private final NoticeMapper noticeMapper;
    @Bean
    public Consumer<Notice> notice(){
        return noticeMapper::insert;
    }
}
