package com.fitassist.backend.repository;

import com.fitassist.backend.model.WaterRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterRepository extends JpaRepository<WaterRecord, Long> {
    // YENİ: Sadece giriş yapan kullanıcının e-postasına göre kayıtları getirir
    List<WaterRecord> findByUserEmail(String email);
    
    // YENİ: Sadece giriş yapan kullanıcının belirli bir tarihteki kayıtlarını getirir
    List<WaterRecord> findByUserEmailAndDate(String email, String date);
}