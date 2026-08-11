package com.fitassist.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "workout_records")
@Data 
public class WorkoutRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String exerciseName; 

    // YENİ: Push, Pull veya Leg kategorisini tutacak alan
    @Column(nullable = false)
    private String category; 

    private Double weight; 
    private Integer sets;  
    private Integer reps;  

    @Column(nullable = false)
    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDate.now();
        }
    }
}