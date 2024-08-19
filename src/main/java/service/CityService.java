package com.example.sugestaoCidades.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sugestaoCidades.model.City;
import com.example.sugestaoCidades.repository.CityRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
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
                .filter(city -> city.getTags().stream().anyMatch(tags::contains))
                .findFirst()
                .map(Collections::singletonList) // Retorna a cidade como uma lista contendo um único elemento
                .orElse(Collections.emptyList()); // Retorna uma lista vazia se nenhuma cidade for encontrada
    }

    public City getCityByThreeTags(Set<String> tags) {
        if (tags.size() != 3) {
            throw new IllegalArgumentException("Você deve fornecer exatamente três tags.");
        }

        return cityRepository.findAll().stream()
                .sorted((city1, city2) -> {
                    long count1 = city1.getTags().stream().filter(tags::contains).count();
                    long count2 = city2.getTags().stream().filter(tags::contains).count();
                    return Long.compare(count2, count1); // Ordena pela maior correspondência
                })
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Nenhuma cidade encontrada."));
    }


    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public List<City> saveAllCities(List<City> cities) {
        return cityRepository.saveAll(cities);
    }
}