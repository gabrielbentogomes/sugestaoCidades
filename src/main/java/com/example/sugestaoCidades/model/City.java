package com.example.sugestaoCidades.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ElementCollection
    private List<String> tags;

    @Embedded
    @ElementCollection
    private List<Attraction> attraction;

}
