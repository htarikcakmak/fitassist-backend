package com.fitassist.backend.repository;

import com.fitassist.backend.model.NutritionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NutritionLogRepository extends JpaRepository<NutritionLog, Long> {
    
    // YENİ: Hem kullanıcının e-postasına hem de tarihe göre filtreleme yapan akıllı sorgu
    List<NutritionLog> findByUserEmailAndDate(String email, LocalDate date);
}