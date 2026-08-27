package com.fitassist.backend.repository;

import com.fitassist.backend.model.WorkoutRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutRecord, Long> {
    
    // YENİ: Sadece e-posta adresi eşleşen kullanıcının tüm antrenman geçmişini getirir
    List<WorkoutRecord> findByUserEmail(String email);

    // YENİ: Sadece giriş yapan kullanıcının belirli bir tarihteki (bugünkü) kayıtlarını getirir
    List<WorkoutRecord> findByUserEmailAndDate(String email, String date);
}