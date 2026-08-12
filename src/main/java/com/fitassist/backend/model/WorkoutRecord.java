package com.fitassist.backend.model;

import jakarta.persistence.*;

@Entity
public class WorkoutRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exerciseName;
    private Integer weight;
    private Integer sets;
    private Integer reps;
    
    // YENİ EKLENEN ALANLAR (Hatanın sebebi buranın eksik olmasıydı)
    private String category; // Push, Pull, Leg verisi için
    private String date;     // Antrenman tarihi için

    public WorkoutRecord() {}

    // GETTER VE SETTER METODLARI
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }

    public Integer getSets() { return sets; }
    public void setSets(Integer sets) { this.sets = sets; }

    public Integer getReps() { return reps; }
    public void setReps(Integer reps) { this.reps = reps; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}