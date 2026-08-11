package com.fitassist.backend.repository;

import com.fitassist.backend.model.WaterRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WaterRepository extends JpaRepository<WaterRecord, Long> {
    // Sadece belirli bir tarihe (bugüne) ait su kayıtlarını bulmak için
    List<WaterRecord> findByDate(LocalDate date);
}