package com.fitassist.backend.repository;

import com.fitassist.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Spring Boot'un harika özelliği: Bu isimlendirme sayesinde özel SQL kodu yazmadan
    // veritabanında e-posta adresine göre kullanıcı arayabileceğiz (Giriş yaparken lazım olacak)
    Optional<User> findByEmail(String email);
    
    // Bir e-postanın sistemde zaten kayıtlı olup olmadığını kontrol etmek için
    boolean existsByEmail(String email);
}