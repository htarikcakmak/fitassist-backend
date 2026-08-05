package com.fitassist.backend.repository;

import com.fitassist.backend.model.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    
    // Bir antrenman gününde birden fazla hareket ve set girileceği için
    // sonuçları yine bir Liste olarak alıyoruz.
    List<WorkoutLog> findByDate(LocalDate date);
}