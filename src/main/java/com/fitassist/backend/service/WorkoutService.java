package com.fitassist.backend.service;

import com.fitassist.backend.model.WorkoutRecord;
import com.fitassist.backend.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    // 1. Bugünün antrenman hareketlerini listele
    public List<WorkoutRecord> getTodaysWorkouts() {
        return workoutRepository.findByDate(LocalDate.now());
    }

    // 2. Yeni bir antrenman hareketi kaydet
    public WorkoutRecord addWorkout(WorkoutRecord record) {
        return workoutRepository.save(record);
    }

    // 3. Yanlış eklenen bir hareketi ID'sine göre sil
    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    // Tüm geçmiş antrenman kayıtlarını getirir (Geçmiş sekmesi için)
    public List<WorkoutRecord> getAllWorkouts() {
        return workoutRepository.findAll();
    }
}