package com.fitassist.backend.service;

import com.fitassist.backend.model.User;
import com.fitassist.backend.repository.UserRepository;
import com.fitassist.backend.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
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

    @Transactional
    public User updateUserProfile(Long id, User updatedData, String currentUserEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // GÜÇLENDİRİLDİ: E-posta büyük/küçük harf duyarsız kontrol ediliyor (equalsIgnoreCase)
        if (!user.getEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new RuntimeException("Bu profili güncelleme yetkiniz yok!");
        }

        // DÜZELTİLDİ: Arayüzden isim değişikliği geldiğinde veritabanına kaydetmesi sağlandı
        if (updatedData.getName() != null) {
            user.setName(updatedData.getName());
        }
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