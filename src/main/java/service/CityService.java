package com.example.sugestaoCidades.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sugestaoCidades.model.City;
import com.example.sugestaoCidades.repository.CityRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public List<City> getCitiesByTags(Set<String> tags) {
        return cityRepository.findAll().stream()
                .filter(city -> city.getTags().stream().filter(tags::contains).count() >= 1)
                .collect(Collectors.toList());
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public List<City> saveAllCities(List<City> cities) {
        return cityRepository.saveAll(cities);
    }
}