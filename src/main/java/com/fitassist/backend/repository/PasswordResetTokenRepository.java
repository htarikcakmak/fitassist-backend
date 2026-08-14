package com.fitassist.backend.repository;

import com.fitassist.backend.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
    // Gelen token metnine göre veritabanından ilgili nesneyi bulmamızı sağlar
    PasswordResetToken findByToken(String token);
    
}