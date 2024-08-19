package com.example.sugestaoCidades.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.sugestaoCidades.model.City;
import com.example.sugestaoCidades.service.CityService;


import java.util.List;
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

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityService.saveCity(city);
    }

    @PostMapping("/bulk")
    public List<City> createCities(@RequestBody List<City> cities) {
        return cityService.saveAllCities(cities);
    }
    
}
