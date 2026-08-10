package com.fitassist.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "nutrition_logs")
@Data // Lombok: Getter ve Setter metodlarını otomatik oluşturur
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

    // Bu kaydın hangi gün eklendiğini tutmak için tarih sütunu
    @Column(nullable = false)
    private LocalDate date;

    // Veritabanına kayıt yapılmadan hemen önce otomatik olarak bugünün tarihini atar
    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDate.now();
        }
    }
}