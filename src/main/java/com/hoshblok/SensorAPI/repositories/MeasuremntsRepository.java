package com.hoshblok.SensorAPI.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoshblok.SensorAPI.models.Measurement;

@Repository
public interface MeasuremntsRepository extends JpaRepository<Measurement, Integer> {

	List<Measurement> findByUsername(String sensor_username);
}
