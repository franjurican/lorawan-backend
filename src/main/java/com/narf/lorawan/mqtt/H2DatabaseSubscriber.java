package com.narf.lorawan.mqtt;

import java.util.logging.Logger;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; 

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.narf.lorawan.persistence.repository.MjerenjaRepository;
import com.narf.lorawan.persistence.entity.Mjerenje;


@Component
public class H2DatabaseSubscriber implements SubscriberHandler {

    private Logger log;
    private MjerenjaRepository mjerenjaRepo;


    @Autowired
    H2DatabaseSubscriber(MjerenjaRepository mjerenjaRepository) {
        log = Logger.getLogger(H2DatabaseSubscriber.class.getName());
        this.mjerenjaRepo = mjerenjaRepository;
        log.info("Created subscriber handler - H2 database, single thread");
    }

    public void processMessage(String topic, MqttMessage message) {
        System.out.println("Message arrived on topic: " + topic);

        // read message and convert message to JSON  
        String jsonString = new String(message.getPayload());
        JSONObject json = new JSONObject(jsonString);
        System.out.println(jsonString);

        // get payload data
        JSONObject payload = json.getJSONObject("phyPayload");

        // save data
        this.mjerenjaRepo.save(new Mjerenje(payload.getString("EUI"), payload.getInt("T"), payload.getInt("RH")));
    }
    
}
