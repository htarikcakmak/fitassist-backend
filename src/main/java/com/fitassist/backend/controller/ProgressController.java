package com.fitassist.backend.controller;

import com.fitassist.backend.model.ProgressLog;
import com.fitassist.backend.repository.ProgressLogRepository;
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
@RequestMapping("/api/progress")
@CrossOrigin(origins = "http://localhost:5173")
public class ProgressController {

    @Autowired
    private ProgressLogRepository progressLogRepository;

    // Çeviri mesajlarını okumamızı sağlayan araç
    @Autowired
    private MessageSource messageSource;

    // GET İsteği: Grafiği çizmek için geçmişteki tüm gelişim kayıtlarını listeler
    @GetMapping("/all")
    public List<ProgressLog> getAllProgressLogs() {
        // Veritabanındaki tüm tartım kayıtlarını getirir
        return progressLogRepository.findAll();
    }

    // POST İsteği: Yeni bir tartım/ölçüm yapıldığında bunu veritabanına ekler
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addProgressLog(@RequestBody ProgressLog newLog) {
        
        Map<String, Object> response = new HashMap<>();

        try {
            // Eğer tarihsiz gönderildiyse bugünün tarihini ata
            if (newLog.getDate() == null) {
                newLog.setDate(LocalDate.now());
            }
            
            // Veritabanına kaydet
            ProgressLog savedLog = progressLogRepository.save(newLog);

            // Başarı mesajını o anki seçili dile göre çek
            String successMessage = messageSource.getMessage(
                    "progress.save.success", 
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