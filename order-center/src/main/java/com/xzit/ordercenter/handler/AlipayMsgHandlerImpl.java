package com.xzit.ordercenter.handler;

import com.alipay.api.msg.MsgHandler;
import org.springframework.stereotype.Component;

@Component
public class AlipayMsgHandlerImpl implements MsgHandler {
    @Override
    public   void   onMessage (String msgApi, String msgId, String bizContent)   {
        System.out.println( "receive message. msgApi:"  + msgApi +  " msgId:"  + msgId +  " bizContent:"  + bizContent);
    }
}
