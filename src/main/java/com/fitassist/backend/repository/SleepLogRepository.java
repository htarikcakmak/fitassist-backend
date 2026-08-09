package com.fitassist.backend.repository;

import com.fitassist.backend.model.SleepLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepLogRepository extends JpaRepository<SleepLog, Long> {
    // JpaRepository sayesinde kaydetme (save) ve listeleme (findAll) işlemleri hazır gelir.
}