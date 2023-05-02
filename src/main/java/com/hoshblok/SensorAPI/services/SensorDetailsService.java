package com.hoshblok.SensorAPI.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;
import com.hoshblok.SensorAPI.security.SensorDetails;

@Service
public class SensorDetailsService implements UserDetailsService{
	
	private SensorsRepository sensorsRepository;

	@Autowired
	public SensorDetailsService(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Sensor> sensor =  sensorsRepository.findByUsername(username);
		
		if(sensor.isEmpty()) {
			throw new UsernameNotFoundException("Sensor not found!");
		}
		
		return new SensorDetails(sensor.get());
	}

}
