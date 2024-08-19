package com.example.sugestaoCidades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sugestaoCidades.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
