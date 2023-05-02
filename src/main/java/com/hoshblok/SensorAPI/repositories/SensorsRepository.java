package com.hoshblok.SensorAPI.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoshblok.SensorAPI.models.Sensor;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer>{

	Optional<Sensor> findByUsername(String name);
}
