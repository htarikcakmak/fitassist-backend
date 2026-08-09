package com.fitassist.backend.controller;

import com.fitassist.backend.model.SleepLog;
import com.fitassist.backend.repository.SleepLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sleep")
@CrossOrigin(origins = "http://localhost:5173") // Vite portuna göre uyarlandı
public class SleepController {

    @Autowired
    private SleepLogRepository sleepLogRepository;

    // Çeviri mesajlarını okumamızı sağlayan araç
    @Autowired
    private MessageSource messageSource;

    // React sayfası ilk açıldığında geçmiş verileri çekmek için çalışır
    @GetMapping("/all")
    public List<SleepLog> getAllSleepLogs() {
        return sleepLogRepository.findAll();
    }

    // React'teki formdan gelen yeni uyku verisini kaydetmek için çalışır
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addSleepLog(@RequestBody SleepLog sleepLog) {
        
        Map<String, Object> response = new HashMap<>();

        try {
            // Veriyi veritabanına kaydet
            SleepLog savedLog = sleepLogRepository.save(sleepLog);

            // Başarı mesajını o anki seçili dile göre çek
            String successMessage = messageSource.getMessage(
                    "sleep.save.success", 
                    null, 
                    LocaleContextHolder.getLocale()
            );

            // Yanıt paketini oluştur
            response.put("message", successMessage);
            response.put("data", savedLog);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Hata mesajını o anki seçili dile göre çek
            String errorMessage = messageSource.getMessage(
                    "server.error", 
                    null, 
                    LocaleContextHolder.getLocale()
            );
            
            response.put("message", errorMessage);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}