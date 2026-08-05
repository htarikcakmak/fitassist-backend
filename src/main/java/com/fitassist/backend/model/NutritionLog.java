package com.fitassist.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "nutrition_logs")
public class NutritionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String mealName; // Örn: Kahvaltı, Öğle, Akşam
    private String foodName;
    private Integer calories;
    private Double protein;
    private Double carbs;
    private Double fats;
}