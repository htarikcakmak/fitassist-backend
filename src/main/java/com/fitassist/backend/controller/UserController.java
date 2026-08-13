package com.fitassist.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.fitassist.backend.model.User;
import com.fitassist.backend.repository.UserRepository;
import com.fitassist.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") 
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Şifreleme, Kimlik Doğrulama ve Token işlemleri için gerekli araçları dahil ediyoruz
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // 1. KAYIT OLMA (REGISTER)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // E-posta kullanılıyor mu kontrolü
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bu e-posta adresi zaten kullanılıyor!"));
        }

        // Şifreyi BCrypt ile şifreleyerek veritabanına kaydediyoruz
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        // Kullanıcı kayıt olur olmaz otomatik giriş yapması için ona Token üretiyoruz
        String jwtToken = jwtUtil.generateToken(savedUser.getEmail());

        Map<String, Object> response = buildAuthResponse(savedUser, jwtToken);
        return ResponseEntity.ok(response);
    }

    // 2. GİRİŞ YAPMA (LOGIN)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginData) {
        try {
            // Spring Security üzerinden E-posta ve Şifre kontrolü yapıyoruz
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword())
            );
        } catch (Exception e) {
            // Şifre veya E-posta yanlışsa hata döndür
            return ResponseEntity.badRequest().body(Map.of("message", "E-posta veya şifre hatalı!"));
        }

        // Giriş başarılıysa kullanıcıyı veritabanından bul
        User user = userRepository.findByEmail(loginData.getEmail()).get();
        
        // Başarılı giriş için yepyeni bir Token üret
        String jwtToken = jwtUtil.generateToken(user.getEmail());

        Map<String, Object> response = buildAuthResponse(user, jwtToken);
        return ResponseEntity.ok(response);
    }

    // 3. PROFİL GÜNCELLEME
    @PutMapping("/update/{id}")
     public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody User updatedData) {
      return userRepository.findById(id)
        // Dönüş tipinin Object barındıran bir ResponseEntity olacağını belirtiyoruz
        .<ResponseEntity<Object>>map(user -> {
            
            user.setHeight(updatedData.getHeight());
            user.setWeight(updatedData.getWeight());
            user.setAge(updatedData.getAge());
            user.setGoal(updatedData.getGoal());
            user.setImageUrl(updatedData.getImageUrl());
            user.setLanguage(updatedData.getLanguage());
            user.setThemeBg(updatedData.getThemeBg());
            user.setThemePrimary(updatedData.getThemePrimary());
            
            userRepository.save(user);
            return ResponseEntity.ok(user);
        })
        // orElseGet kullanımı performansı artırır, sadece nesne yoksa çalışır
        .orElseGet(() -> ResponseEntity.badRequest().body("Kullanıcı bulunamadı")); 
    }

    // React ön yüzünün anlayacağı standart bir yanıt formatı oluşturmak için yardımcı metod
    private Map<String, Object> buildAuthResponse(User user, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("token", token); // İşte sihirli anahtarımız!
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("height", user.getHeight());
        response.put("weight", user.getWeight());
        response.put("age", user.getAge());
        response.put("goal", user.getGoal());
        response.put("imageUrl", user.getImageUrl());
        response.put("language", user.getLanguage());
        response.put("themeBg", user.getThemeBg());
        response.put("themePrimary", user.getThemePrimary());
        return response;
    }
}