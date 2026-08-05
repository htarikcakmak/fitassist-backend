package com.fitassist.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "workout_logs")
public class WorkoutLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String programType; // Örn: Push, Pull, Leg
    private String exerciseName;
    private Integer setNumber;
    private Double weight;
    private Integer reps;
}