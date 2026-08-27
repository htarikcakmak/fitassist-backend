package com.fitassist.backend.repository;

import com.fitassist.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Giriş yapma (Login), Profil Güncelleme ve JWT Token işlemleri sırasında
    // kullanıcının e-posta adresine göre veritabanından güvenli bir şekilde bulunmasını sağlar.
    Optional<User> findByEmail(String email);
    
    // Kayıt olma (Register) işlemi sırasında aynı e-posta adresiyle 
    // ikinci bir hesabın açılmasını engellemek için hızlı bir varlık (true/false) kontrolü yapar.
    boolean existsByEmail(String email);
}