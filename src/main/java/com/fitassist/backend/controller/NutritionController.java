package com.fitassist.backend.controller;

import com.fitassist.backend.model.NutritionLog;
import com.fitassist.backend.repository.NutritionLogRepository;
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
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = "http://localhost:5173")
public class NutritionController {

    @Autowired
    private NutritionLogRepository nutritionLogRepository;

    @Autowired
    private MessageSource messageSource;

    // GET İsteği: Uygulama açıldığında bugünün tüm besin kayıtlarını getirir
    @GetMapping("/today")
    public List<NutritionLog> getTodayNutritionLogs() {
        LocalDate today = LocalDate.now();
        // Bugünün tarihine sahip tüm öğünleri veritabanından bulup liste halinde döner
        return nutritionLogRepository.findByDate(today);
    }

    // POST İsteği: React üzerinden yeni bir besin eklendiğinde çalışır
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addNutritionLog(@RequestBody NutritionLog newLog) {
        
        // try-catch bloğu tamamen kaldırıldı!
        
        Map<String, Object> response = new HashMap<>();

        // 1. Eğer React tarafından tarih gönderilmediyse, bugünün tarihini otomatik ekle
        if (newLog.getDate() == null) {
            newLog.setDate(LocalDate.now());
        }
        
        // 2. Gelen yeni besin verisini veritabanına kaydet
        NutritionLog savedLog = nutritionLogRepository.save(newLog);

        // 3. Başarı mesajını o anki seçili dile göre çek
        String successMessage = messageSource.getMessage(
                "nutrition.save.success", 
                null, 
                LocaleContextHolder.getLocale()
        );

        // 4. Yanıt paketini oluştur ve React'e gönder
        response.put("message", successMessage);
        response.put("data", savedLog);

        return ResponseEntity.ok(response);
    }
}