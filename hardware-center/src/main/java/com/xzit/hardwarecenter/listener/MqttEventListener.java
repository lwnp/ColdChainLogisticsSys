package com.xzit.hardwarecenter.listener;

import org.springframework.context.event.EventListener;
import org.springframework.integration.mqtt.core.MqttPahoComponent;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;
import org.springframework.stereotype.Component;

@Component
public class MqttEventListener {

    @EventListener
    public void handleMqttConnectionFailedEvent(MqttConnectionFailedEvent event) {
        MqttPahoComponent source = event.getSourceAsType();
        String beanName = source.getBeanName();
        String disconnectReason = event.getCause() != null ? event.getCause().getMessage() : "Unknown";

        System.out.println("MQTT Connection failed on bean: " + beanName);
        System.out.println("Disconnect reason: " + disconnectReason);
    }
}