package com.fitassist.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "nutrition_logs")
@Data 
public class NutritionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mealName; // Örn: "Kahvaltı", "Öğle", "Akşam"

    @Column(nullable = false)
    private String foodName; // Örn: "Yulaf Ezmesi (100 gram)"

    private Integer calories;
    private Double protein;
    private Double carbs;
    private Double fats;

    @Column(nullable = false)
    private LocalDate date;

    // YENİ: Bu yemeği hangi kullanıcının yediğini belirten mühür
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore // React'e veri gönderirken sonsuz döngü çökmesini engeller
    private User user;

    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDate.now();
        }
    }
}