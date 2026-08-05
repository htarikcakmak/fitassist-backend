package com.fitassist.backend.repository;

import com.fitassist.backend.model.ProgressLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProgressLogRepository extends JpaRepository<ProgressLog, Long> {
    
    // O güne ait tek bir tartım/ölçüm kaydı getirmek için kullanılır.
    Optional<ProgressLog> findByDate(LocalDate date);
}