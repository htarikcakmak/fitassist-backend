package com.fitassist.backend.repository;

import com.fitassist.backend.model.WorkoutRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutRecord, Long> {
    
    // Sadece belirli bir gündeki (örneğin bugünkü) antrenman hareketlerini getiren özel sorgu
    List<WorkoutRecord> findByDate(LocalDate date);
}