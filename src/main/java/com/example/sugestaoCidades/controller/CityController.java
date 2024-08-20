package com.example.sugestaoCidades.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        // Verifica se exatamente três tags foram fornecidas
        if (tags.size() != 3) {
            return ResponseEntity.badRequest().body(null); // Retorna um erro 400 se o número de tags não for 3
        }

        try {
            // Chama o serviço para obter a cidade correspondente
            City city = cityService.getCityByThreeTags(tags);
            return ResponseEntity.ok(city); // Retorna a cidade encontrada com um status 200
        } catch (NoSuchElementException e) {
            // Retorna um erro 404 se nenhuma cidade for encontrada
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Retorna um erro 500 para quaisquer outras exceções inesperadas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
