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
@CrossOrigin(origins = "http://localhost:5173") // Vite portuna göre güncellendi
public class SleepController {

    @Autowired
    private SleepLogRepository sleepLogRepository;

    @Autowired
    private MessageSource messageSource;

    // GET İsteği: Geçmiş verileri çekmek için çalışır (Değişmedi)
    @GetMapping("/all")
    public List<SleepLog> getAllSleepLogs() {
        return sleepLogRepository.findAll();
    }

    // POST İsteği: Yeni uyku verisini kaydeder
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addSleepLog(@RequestBody SleepLog sleepLog) {
        
        // try-catch kaldırıldı!
        
        Map<String, Object> response = new HashMap<>();

        // 1. Veriyi veritabanına kaydet
        SleepLog savedLog = sleepLogRepository.save(sleepLog);

        // 2. Başarı mesajını o anki seçili dile göre çek (messages.properties dosyasından)
        String successMessage = messageSource.getMessage(
                "sleep.save.success", 
                null, 
                LocaleContextHolder.getLocale()
        );

        // 3. Yanıt paketini oluştur
        response.put("message", successMessage);
        response.put("data", savedLog);

        return ResponseEntity.ok(response);
    }
}