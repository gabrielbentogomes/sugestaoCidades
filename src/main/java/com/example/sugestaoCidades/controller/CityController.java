package com.example.sugestaoCidades.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sugestaoCidades.model.City;
import com.example.sugestaoCidades.service.CityService;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/by-tags")
    public List<City> getCitiesByTags(@RequestParam Set<String> tags) {
        return cityService.getCitiesByTags(tags);
    }

    @GetMapping("/by-three-tags")
    public ResponseEntity<City> getCityByThreeTags(@RequestParam Set<String> tags) {
        try {
            City city = cityService.getCityByThreeTags(tags);
            return ResponseEntity.ok(city);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityService.saveCity(city);
    }

    @PostMapping("/bulk")
    public List<City> createCities(@RequestBody List<City> cities) {
        return cityService.saveAllCities(cities);
    }
    
}
