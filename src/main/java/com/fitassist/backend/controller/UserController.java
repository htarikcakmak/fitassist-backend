package com.fitassist.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fitassist.backend.model.User;
import com.fitassist.backend.repository.UserRepository;
import com.fitassist.backend.security.JwtUtil;
import com.fitassist.backend.dto.LoginRequest;
import com.fitassist.backend.dto.RegisterRequest;
import com.fitassist.backend.model.PasswordResetToken;
import com.fitassist.backend.repository.PasswordResetTokenRepository;
import com.fitassist.backend.service.UserService;

import java.security.Principal;
import java.util.UUID;
import java.util.Date;
import java.util.Calendar;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") 
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bu e-posta adresi ile kayıtlı kullanıcı bulunamadı."));
        }

        User user = userOptional.get();
        String token = UUID.randomUUID().toString();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 15);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(calendar.getTime());

        tokenRepository.save(resetToken);

        System.out.println("----- ŞİFRE SIFIRLAMA LİNKİ -----");
        System.out.println("Kullanıcı: " + email);
        System.out.println("Link: http://localhost:5173/reset-password?token=" + token);
        System.out.println("---------------------------------");

        return ResponseEntity.ok(Map.of("message", "Şifre sıfırlama bağlantısı oluşturuldu. Konsolu kontrol edin."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Geçersiz veya hatalı anahtar."));
        }

        if (resetToken.getExpiryDate().before(new Date())) {
            tokenRepository.delete(resetToken);
            return ResponseEntity.badRequest().body(Map.of("message", "Bu bağlantının süresi dolmuş. Lütfen tekrar talep edin."));
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);

        return ResponseEntity.ok(Map.of("message", "Şifreniz başarıyla güncellendi."));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok(Map.of("message", "Kayıt başarıyla oluşturuldu!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Kayıt hatası: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginData) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "E-posta veya şifre hatalı!"));
        }

        User user = userRepository.findByEmail(loginData.getEmail()).get();
        String jwtToken = jwtUtil.generateToken(user.getEmail());

        Map<String, Object> response = buildAuthResponse(user, jwtToken);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody User updatedData, Principal principal) {
        // YENİ GÜVENLİK KALKANI: Token yoksa veya süresi dolmuşsa çökmeyi engeller
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Yetkisiz işlem: Geçerli bir oturum bulunamadı. Lütfen tekrar giriş yapın."));
        }

        try {
            User updatedUser = userService.updateUserProfile(id, updatedData, principal.getName());
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            if(e.getMessage().contains("yetkiniz yok")) {
                return ResponseEntity.status(403).body(Map.of("message", e.getMessage()));
            }
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    private Map<String, Object> buildAuthResponse(User user, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("token", token); 
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