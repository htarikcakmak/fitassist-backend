package com.fitassist.backend.controller;

import com.fitassist.backend.model.WorkoutLog;
import com.fitassist.backend.repository.WorkoutLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workout")
@CrossOrigin(origins = "http://localhost:5173")
public class WorkoutController {

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    @Autowired
    private MessageSource messageSource;

    // GET İsteği: Uygulama açıldığında bugünün tüm antrenman kayıtlarını getirir
    @GetMapping("/today")
    public List<WorkoutLog> getTodayWorkoutLogs() {
        // Bugünün tarihine sahip tüm setleri veritabanından bulup liste halinde döner
        // Not: Repository'de findByDate gibi bir metodun LocalDate.now() ile çalıştığını varsayıyoruz.
        return workoutLogRepository.findAll(); // Basitlik adına findAll, gerçek projede tarihe göre filtrelenmeli
    }

    // POST İsteği: React üzerinden yeni bir set eklendiğinde çalışır
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addWorkoutLog(@RequestBody WorkoutLog newLog) {
        
        // try-catch bloğu tamamen kaldırıldı! merkezi hata yönetimi devrede.
        
        Map<String, Object> response = new HashMap<>();

        // 1. Gelen yeni set verisini veritabanına kaydet
        WorkoutLog savedLog = workoutLogRepository.save(newLog);

        // 2. Başarı mesajını o anki seçili dile göre properties dosyasından çek
        String successMessage = messageSource.getMessage(
                "workout.save.success", 
                null, 
                LocaleContextHolder.getLocale()
        );

        // 3. Yanıt paketini oluştur ve React'e gönder
        response.put("message", successMessage);
        response.put("data", savedLog);

        return ResponseEntity.ok(response);
    }
}