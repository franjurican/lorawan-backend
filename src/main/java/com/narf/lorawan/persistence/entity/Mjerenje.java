package com.narf.lorawan.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mjerenja")
public class Mjerenje {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String eui;
    private Integer temperatura;
    private Integer vlaga;

    protected Mjerenje() {}

    public Mjerenje(String eui, Integer temperatura, Integer vlaga) {
        this.eui = eui;
        this.temperatura = temperatura;
        this.vlaga = vlaga;
    }

    @Override
    public String toString() {
        return String.format("Mjerenje[id=%d, eui=%s, temperatura=%d, vlaga=%d]", this.id, this.eui, this.temperatura, this.vlaga);
    }

    public Long getId() {
        return this.id;
    }

    public String getDeviceEUI() {
        return this.eui;
    }

    public Integer getTemperature() {
        return this.temperatura;
    }

    public Integer getHumidity() {
        return this.vlaga;
    }
}
