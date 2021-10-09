package com.narf.lorawan.mqtt;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import java.util.UUID;
import java.util.logging.Logger;


@Component
public class PahoClient implements MqttCallback {
    
    private String serverURI = "tcp://localhost:1883";
    private String topicName = "gateway/+/event/up";
    private String clientID = "aplikacija-" + UUID.randomUUID().toString();
    private Integer qos = 0;
    private Integer subscribeTimeout = 1000;

    private Logger log;
    private MqttAsyncClient client;
    private MqttConnectOptions connectOptions;
    private MqttClientPersistence dataStore;
    private SubscriberHandler messageHandler;
    
    
    @Autowired
    PahoClient(SubscriberHandler messageHandler) {
        // create logger, data store, connection options and message handler
        log = Logger.getLogger(PahoClient.class.getName());
        this.dataStore = new MemoryPersistence();
        this.connectOptions = connectOptions();
        this.messageHandler = messageHandler;
        
        // connect to server and subscribe to topic
        try {

            // create client
            client = new MqttAsyncClient(serverURI, clientID, dataStore);
            client.setCallback(this);

            // connect client to server and subscribe client to topic
            log.info("Connecting to MQTT broker as " + clientID + " ...");
            connectClient2Broker(this.client, this.connectOptions);
            log.info("Subscribing to topic " + topicName + " ...");
            subscribeClient2Topic(this.client, this.topicName, this.qos, this.subscribeTimeout);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    private MqttConnectOptions connectOptions() {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setCleanSession(true);
        connectOptions.setConnectionTimeout(10);
        connectOptions.setKeepAliveInterval(30);
        return connectOptions;
    }


    private void connectClient2Broker(MqttAsyncClient client, MqttConnectOptions connectOptions) throws MqttException {
        client.connect(connectOptions, null, new IMqttActionListener() {
                
            public void onSuccess(IMqttToken token) {
                log.info("Connected to MQTT broker!");
            }

            public void onFailure(IMqttToken token, Throwable e) {
                log.warning("Failed to connect to MQTT broker!" );
            }
        }).waitForCompletion();
    }


    private void subscribeClient2Topic(MqttAsyncClient client, String topicName, Integer qos, Integer timeout) throws MqttException {
        client.subscribe(topicName, qos, null, new IMqttActionListener() {
                
            public void onSuccess(IMqttToken token) {
                log.info("Subscribed to topic!");
            }

            public void onFailure(IMqttToken token, Throwable e) {
                log.warning("Failed to subscribe to topic!");
            }
        }).waitForCompletion(timeout);
    }


    // MQTT event callbacks
    @Override
    public void connectionLost(Throwable cause) {
        log.warning("Connection with MQTT broker: " + serverURI + " lost!");
    }


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        this.messageHandler.processMessage(topic, message);
    }

    
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // empty
    }
}
