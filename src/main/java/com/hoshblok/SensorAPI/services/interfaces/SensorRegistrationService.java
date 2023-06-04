package com.hoshblok.SensorAPI.services.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.dto.sensor.request.SensorRegistrationRequest;

public interface SensorRegistrationService {

	void register(SensorRegistrationRequest request);

}