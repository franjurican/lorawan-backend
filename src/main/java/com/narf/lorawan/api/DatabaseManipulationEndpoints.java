package com.narf.lorawan.api;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.narf.lorawan.persistence.entity.Mjerenje;
import com.narf.lorawan.persistence.repository.MjerenjaRepository;


@CrossOrigin
@RestController
public class DatabaseManipulationEndpoints {

    @Autowired
    private MjerenjaRepository repository;

    @GetMapping("/mjerenja")
	public List<Mjerenje> multipleJsonMeasurements() {
        return repository.findAll();
	} 

    @GetMapping("/mjerenje")
    public Mjerenje jsonMeasurement() {
        // TODO: pass device EUI to API call
        return repository.findByEui("AE:4A:5B:8D:62:C1");
    }
}
