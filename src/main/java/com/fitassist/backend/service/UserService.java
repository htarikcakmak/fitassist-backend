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

        // Null hatalarını önlemek için kontrollerle atama yapıyoruz
        user.setHeight(updatedData.getHeight() != null ? updatedData.getHeight() : 0);
        user.setWeight(updatedData.getWeight() != null ? updatedData.getWeight() : 0);
        user.setAge(updatedData.getAge() != null ? updatedData.getAge() : 0);
        user.setGoal(updatedData.getGoal());
        user.setImageUrl(updatedData.getImageUrl());
        user.setLanguage(updatedData.getLanguage());
        user.setThemeBg(updatedData.getThemeBg());
        user.setThemePrimary(updatedData.getThemePrimary());

        return userRepository.save(user);
    }
}