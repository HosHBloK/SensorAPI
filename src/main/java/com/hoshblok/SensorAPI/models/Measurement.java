package com.hoshblok.SensorAPI.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "measurements")
public class Measurement {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "value")
	@NotNull(message = "Value should not be empty")
	@Min(value = -100, message = "Value should be above -100!")
	@Max(value = 100, message = "Value should be under 100!")
	private Float value;

	@Column(name = "raining")
	@NotNull(message = "Raining should not be empty")
	private Boolean raining;

	@ManyToOne()
	@JoinColumn(name = "sensor_id", referencedColumnName = "id")
	@NotNull(message = "Sensor should not be empty")
	private Sensor sensor;

	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	
	public Measurement(Float value, Boolean raining, Sensor sensor) {
		this.value = value;
		this.raining = raining;
		this.sensor = sensor;
	}
	
	public Measurement() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Boolean isRaining() {
		return raining;
	}

	public void setRaining(Boolean raining) {
		this.raining = raining;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
