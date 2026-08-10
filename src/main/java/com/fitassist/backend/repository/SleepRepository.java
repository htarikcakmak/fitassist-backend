package com.fitassist.backend.repository;

import com.fitassist.backend.model.SleepRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepRepository extends JpaRepository<SleepRecord, Long> {
    // Tüm standart veritabanı işlemleri (kaydetme, silme, bulma) JpaRepository sayesinde otomatik gelir.
}