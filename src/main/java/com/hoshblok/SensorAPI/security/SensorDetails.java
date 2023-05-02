package com.hoshblok.SensorAPI.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hoshblok.SensorAPI.models.Sensor;

public class SensorDetails implements UserDetails {

	private final Sensor sensor;

	@Autowired
	public SensorDetails(Sensor sensor) {
		this.sensor = sensor;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(sensor.getRole()));
	}

	@Override
	public String getPassword() {
		return sensor.getPassword();
	}

	@Override
	public String getUsername() {
		return sensor.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
