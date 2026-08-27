package com.fitassist.backend.repository;

import com.fitassist.backend.model.ProgressLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressLogRepository extends JpaRepository<ProgressLog, Long> {
    
    // YENİ: Sadece e-posta adresi eşleşen kullanıcının tüm gelişim kayıtlarını getir
    List<ProgressLog> findByUserEmail(String email);

    // O güne ve belirli bir kullanıcıya ait tek bir tartım kaydı getirmek için
    Optional<ProgressLog> findByUserEmailAndDate(String email, LocalDate date);
}