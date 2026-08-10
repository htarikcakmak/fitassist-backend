package com.fitassist.backend.service; // DÜZELTME: Adres 'backend' olarak güncellendi!

import com.fitassist.backend.model.User;
import com.fitassist.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 1. KAYIT OLMA (REGISTER) İŞLEMİ
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Bu e-posta adresi zaten kullanılıyor!");
        }
        return userRepository.save(user);
    }

    // 2. GİRİŞ YAPMA (LOGIN) İŞLEMİ
    public User loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user; 
            }
        }
        throw new RuntimeException("E-posta adresi veya şifre hatalı!");
    }

    // 3. KULLANICI BİLGİLERİNİ GETİRME
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
    }
}