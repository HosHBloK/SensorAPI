package com.hoshblok.SensorAPI.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoshblok.SensorAPI.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer>{

	Optional<Person> findByUsername(String username);
}
