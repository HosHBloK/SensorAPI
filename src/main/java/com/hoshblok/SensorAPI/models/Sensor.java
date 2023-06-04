package com.hoshblok.SensorAPI.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sensors")
public class Sensor {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "Sensor username should not be empty!")
	@Size(min = 3, max = 30, message = "Sensor username should be between 3 and 30 symbols!")
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	@NotNull(message = "Password should not be null!")
	private String password;

	@Column(name = "role")
	private String role;

	@OneToMany(mappedBy = "sensor")
	private List<Measurement> measurement;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Measurement> getMeasurement() {
		return measurement;
	}

	public void setMeasurement(List<Measurement> measurement) {
		this.measurement = measurement;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}