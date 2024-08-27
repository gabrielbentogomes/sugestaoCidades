package com.example.sugestaoCidades.model;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Attraction {
    private String name_attraction;
    private String description_attraction;
}
