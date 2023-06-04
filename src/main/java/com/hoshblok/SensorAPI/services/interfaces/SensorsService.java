package com.hoshblok.SensorAPI.services.interfaces;

import com.hoshblok.SensorAPI.models.Sensor;

public interface SensorsService {

	Sensor findOne(String name);

}