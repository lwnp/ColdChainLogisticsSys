package com.xzit.hardwarecenter.socket;

import com.alibaba.fastjson2.JSON;
import com.xzit.commonhardware.entity.SensorData;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {
    private Session session;
    private String userId;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static final CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static final ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<String,Session>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")String userId) {
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());
        } catch (Exception ignored) {
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception ignored) {
        }
    }

    @OnMessage
    public void onMessage(String message) {
//        Map<String,Double> gas=new HashMap<>();
//        gas.put("co",25.0);
//        gas.put("n2",30.2);
//        SensorData sensorData= SensorData.builder()
//                .speed(45.0)
//                .temperature(36.0)
//                .location("[84.111541,45.523145]")
//                .gasComposition(gas)
//                .timeStamp(LocalDateTime.now())
//                .build();
//        sendOneMessage("1",JSON.toJSONString(sensorData));
        SensorData sensorData= JSON.parseObject(message, SensorData.class);
        log.info("【websocket消息】收到客户端消息:"+sensorData);
    }

    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误,原因:"+error.getMessage());

    }


    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:"+message);
        for(WebSocket webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception ignored) {

            }
        }
    }

    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null&&session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception ignored) {

            }
        }
    }

    public void sendMoreMessage(String[] userIds, String message) {
        for(String userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception ignored) {
                }
            }
        }

    }
}
