package com.narf.lorawan.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface SubscriberHandler {
    void processMessage(String topic, MqttMessage message);
}
