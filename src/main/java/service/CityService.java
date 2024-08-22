package com.example.sugestaoCidades.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sugestaoCidades.model.City;
import com.example.sugestaoCidades.repository.CityRepository;

import java.util.*;
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

    public List<City> getCitiesByThreeTags(Set<String> tags) {
        if (tags.size() != 3) {
            throw new IllegalArgumentException("Você deve fornecer exatamente três tags.");
        }

        // Filtra cidades que possuem exatamente as três tags fornecidas
        List<City> exactMatchCities = cityRepository.findAll().stream()
                .filter(city -> city.getTags().containsAll(tags) && city.getTags().size() == 3)
                .collect(Collectors.toList());

        // Se houver cidades que correspondem exatamente às três tags, retorna essas cidades
        if (!exactMatchCities.isEmpty()) {
            // Embaralha a lista para garantir a aleatoriedade
            Collections.shuffle(exactMatchCities);
            return exactMatchCities.stream()
                    .limit(2) // Retorna até duas cidades aleatórias
                    .collect(Collectors.toList());
        }

        // Se não houver pelo menos duas correspondências exatas, ordena as cidades por relevância
        List<City> sortedCities = cityRepository.findAll().stream()
                .sorted((city1, city2) -> {
                    long count1 = city1.getTags().stream().filter(tags::contains).count();
                    long count2 = city2.getTags().stream().filter(tags::contains).count();
                    return Long.compare(count2, count1); // Ordena pela maior correspondência
                })
                .collect(Collectors.toList());

        // Embaralha as cidades ordenadas por relevância para garantir a aleatoriedade
        Collections.shuffle(sortedCities);
        return sortedCities.stream()
                .limit(2) // Retorna até duas cidades
                .collect(Collectors.toList());
    }




    public Set<String> getAllTags() {
        return cityRepository.findAll().stream()
                .flatMap(city -> city.getTags().stream()) // Extrai todas as tags das cidades
                .collect(Collectors.toSet()); // Coleta em um Set para evitar duplicatas
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public List<City> saveAllCities(List<City> cities) {
        return cityRepository.saveAll(cities);
    }
}