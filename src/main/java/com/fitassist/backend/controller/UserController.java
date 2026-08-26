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

    // Şifreleme, Kimlik Doğrulama ve Token işlemleri için gerekli araçları dahil ediyoruz
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    // 1. ŞİFREMİ UNUTTUM TALEBİ
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bu e-posta adresi ile kayıtlı kullanıcı bulunamadı."));
        }

        User user = userOptional.get();

        // Rastgele benzersiz bir token (anahtar) üretiyoruz
        String token = UUID.randomUUID().toString();

        // Token için 15 dakikalık geçerlilik süresi belirliyoruz
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 15);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(calendar.getTime());

        // Veritabanına kaydediyoruz
        tokenRepository.save(resetToken);

        // ŞİMDİLİK TEST İÇİN KONSOLA YAZDIRIYORUZ
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        System.out.println("----- ŞİFRE SIFIRLAMA LİNKİ -----");
        System.out.println("Kullanıcı: " + email);
        System.out.println("Link: " + resetLink);
        System.out.println("---------------------------------");

        return ResponseEntity.ok(Map.of("message", "Şifre sıfırlama bağlantısı oluşturuldu. Konsolu kontrol edin."));
    }

    // 2. YENİ ŞİFREYİ BELİRLEME
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Geçersiz veya hatalı anahtar."));
        }

        // Sürenin geçip geçmediğini kontrol ediyoruz
        if (resetToken.getExpiryDate().before(new Date())) {
            tokenRepository.delete(resetToken);
            return ResponseEntity.badRequest().body(Map.of("message", "Bu bağlantının süresi dolmuş. Lütfen tekrar talep edin."));
        }

        // Her şey geçerliyse kullanıcının şifresini güvenli (BCrypt) olarak güncelliyoruz
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Kullanılmış token'ı bir daha kullanılmaması için veritabanından siliyoruz
        tokenRepository.delete(resetToken);

        return ResponseEntity.ok(Map.of("message", "Şifreniz başarıyla güncellendi."));
    }

    // 1. KAYIT OLMA (REGISTER)
    @PostMapping("/register")

    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
    try {
        // 1. E-posta daha önce kullanılmış mı kontrol et
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bu e-posta adresi zaten kullanımda!"));
        }

        // 2. Yeni bir kullanıcı nesnesi oluştur ve DTO'dan gelen verileri aktar
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        
        // KRİTİK ADIM: Şifreyi veritabanına düz metin olarak değil, şifreleyerek (BCrypt) kaydetmeliyiz.
        // Eğer projende passwordEncoder tanımlı değilse bu satır hata verebilir. (Aşağıda belirttim)
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        // 3. Varsayılan (default) değerleri atayabilirsin
        newUser.setGoal("Vücut Kompozisyonu");
        
        // 4. Veritabanına kaydet
        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "Kayıt başarıyla oluşturuldu!"));
        
    } catch (Exception e) {
        // Eğer sunucuda başka bir hata çıkarsa, 500 hatasının nedenini terminale (loglara) yazdırır
        e.printStackTrace(); 
        return ResponseEntity.internalServerError().body(Map.of("message", "Sunucu hatası: " + e.getMessage()));
    }
}

    // 2. GİRİŞ YAPMA (LOGIN)

    @PostMapping("/login")

    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginData) {
     try {
        // Spring Security üzerinden E-posta ve Şifre kontrolü yapıyoruz
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword())
        );
    } catch (Exception e) {
        // Şifre veya E-posta yanlışsa yakala ve frontend'e hata döndür
        return ResponseEntity.badRequest().body(Map.of("message", "E-posta veya şifre hatalı!"));
    }

    // Giriş başarılıysa kullanıcıyı veritabanından e-posta adresine göre bul
    User user = userRepository.findByEmail(loginData.getEmail()).get();
    
    // Başarılı giriş için yepyeni bir güvenlik anahtarı (JWT Token) üret
    String jwtToken = jwtUtil.generateToken(user.getEmail());

    // Yanıtı paketle ve React'e (ön yüze) gönder
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