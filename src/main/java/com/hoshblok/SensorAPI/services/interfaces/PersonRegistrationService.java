package com.hoshblok.SensorAPI.services.interfaces;

import com.hoshblok.SensorAPI.dto.person.request.PersonRegistrationRequest;

public interface PersonRegistrationService {

	void register(PersonRegistrationRequest request);
}