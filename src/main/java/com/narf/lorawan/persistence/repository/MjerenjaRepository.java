package com.narf.lorawan.persistence.repository;

import com.narf.lorawan.persistence.entity.Mjerenje;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MjerenjaRepository extends JpaRepository<Mjerenje, Long> {
    Mjerenje findByEui(String eui);
}
