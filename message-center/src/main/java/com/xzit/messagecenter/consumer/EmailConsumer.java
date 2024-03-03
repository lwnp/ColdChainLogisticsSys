package com.xzit.messagecenter.consumer;

import com.xzit.common.sys.model.dto.EmailDTO;
import com.xzit.messagecenter.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailUtil emailUtil;
    @Bean
    public Consumer<EmailDTO> email(){
        return emailUtil::sendHtmlMail;
    }
}
