package com.fitassist.backend.service;

import com.fitassist.backend.model.User;
import com.fitassist.backend.repository.UserRepository;
import com.fitassist.backend.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. KAYIT OLMA MANTIĞI
    public User registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Bu e-posta adresi zaten kullanımda!");
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(newUser);
    }

    // 2. PROFİL GÜNCELLEME MANTIĞI VE GÜVENLİK
    public User updateUserProfile(Long id, User updatedData, String currentUserEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Güvenlik kontrolü: Güncellenecek profil gerçekten token sahibine mi ait?
        if (!user.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Bu profili güncelleme yetkiniz yok!");
        }

        // AKILLI (KISMİ) GÜNCELLEME ALANI:
        // React'ten hangi veri gönderildiyse (null değilse) sadece o veriyi günceller.
        // Gönderilmeyen verileri atlar ve veritabanındaki mevcut değerli bilgileri (boy, kilo vb.) korur.
        
        if (updatedData.getHeight() != null) {
            user.setHeight(updatedData.getHeight());
        }
        
        if (updatedData.getWeight() != null) {
            user.setWeight(updatedData.getWeight());
        }
        
        if (updatedData.getAge() != null) {
            user.setAge(updatedData.getAge());
        }
        
        if (updatedData.getGoal() != null) {
            user.setGoal(updatedData.getGoal());
        }
        
        if (updatedData.getImageUrl() != null) {
            user.setImageUrl(updatedData.getImageUrl());
        }
        
        if (updatedData.getLanguage() != null) {
            user.setLanguage(updatedData.getLanguage());
        }
        
        if (updatedData.getThemeBg() != null) {
            user.setThemeBg(updatedData.getThemeBg());
        }
        
        if (updatedData.getThemePrimary() != null) {
            user.setThemePrimary(updatedData.getThemePrimary());
        }

        return userRepository.save(user);
    }
}