package com.fitassist.backend.repository;

import com.fitassist.backend.model.NutritionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NutritionLogRepository extends JpaRepository<NutritionLog, Long> {
    
    // Bir günde birden fazla öğün (Kahvaltı, Akşam vb.) olacağı için
    // List (Liste) veri tipini kullanıyoruz. O günkü tüm yediklerini getirir.
    List<NutritionLog> findByDate(LocalDate date);
}