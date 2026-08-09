package com.fitassist.backend.controller;

import com.fitassist.backend.model.WorkoutLog;
import com.fitassist.backend.repository.WorkoutLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workout")
@CrossOrigin(origins = "http://localhost:5173")
public class WorkoutController {

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    // 1. Mesaj çevirilerini okumamızı sağlayan Spring Boot aracı
    @Autowired
    private MessageSource messageSource;

    // GET İsteği: Uygulama açıldığında bugünün kaydedilmiş antrenman verilerini getirir
    @GetMapping("/today")
    public List<WorkoutLog> getTodayWorkoutLogs() {
        LocalDate today = LocalDate.now();
        // Sadece bugünün tarihine sahip hareketleri (set, tekrar, ağırlık) getirir
        return workoutLogRepository.findByDate(today);
    }

    // POST İsteği: React'te bir hareketin seti onaylandığında çalışır
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addWorkoutLog(@RequestBody WorkoutLog newLog) {
        
        // React'e döneceğimiz JSON paketini hazırlıyoruz
        Map<String, Object> response = new HashMap<>();

        try {
            // Tarih kontrolü yap ve yoksa bugünü ata
            if (newLog.getDate() == null) {
                newLog.setDate(LocalDate.now());
            }
            
            // Yeni seti veritabanına kaydet
            WorkoutLog savedLog = workoutLogRepository.save(newLog);

            // 2. Başarı mesajını o anki dile göre çekiyoruz
            String successMessage = messageSource.getMessage(
                    "workout.save.success", 
                    null, 
                    LocaleContextHolder.getLocale() 
            );

            // Başarılı yanıt paketimizi oluşturuyoruz
            response.put("message", successMessage);
            response.put("data", savedLog);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 3. Hata durumunda, hata mesajını o anki dile göre çekiyoruz
            String errorMessage = messageSource.getMessage(
                    "server.error", 
                    null, 
                    LocaleContextHolder.getLocale()
            );
            
            // Hata paketimizi oluşturup HTTP 500 koduyla dönüyoruz
            response.put("message", errorMessage);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}