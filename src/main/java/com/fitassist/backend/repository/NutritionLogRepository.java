package com.fitassist.backend.repository;

import com.fitassist.backend.model.NutritionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NutritionLogRepository extends JpaRepository<NutritionLog, Long> {
    
    // Spring Boot'un harika özelliği: Bu isimlendirme sayesinde sadece metodun adını yazarak
    // veritabanında "Belirli bir tarihteki tüm besin kayıtlarını getir" sorgusunu çalıştırmış oluyoruz.
    List<NutritionLog> findByDate(LocalDate date);
}