package com.fitassist.backend.repository;

import com.fitassist.backend.model.WaterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

// JpaRepository<Hangi Tablo, ID Tipi> şeklinde tanımlanır.
@Repository
public interface WaterLogRepository extends JpaRepository<WaterLog, Long> {
    
    // O güne ait sadece 1 su kaydı olacağı için Optional kullanıyoruz.
    // Eğer kayıt varsa dolu, yoksa boş (null) dönecek.
    Optional<WaterLog> findByDate(LocalDate date);
}