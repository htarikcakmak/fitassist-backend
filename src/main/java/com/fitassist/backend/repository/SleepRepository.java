package com.fitassist.backend.repository;

import com.fitassist.backend.model.SleepRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SleepRepository extends JpaRepository<SleepRecord, Long> {
    // YENİ: Sadece e-posta adresi eşleşen kullanıcının uyku verilerini listele
    List<SleepRecord> findByUserEmail(String email);
}