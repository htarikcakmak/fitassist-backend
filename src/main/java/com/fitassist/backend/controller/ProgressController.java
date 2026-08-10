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

    @Autowired
    private MessageSource messageSource;

    // GET İsteği: Grafiği çizmek için geçmişteki tüm gelişim kayıtlarını listeler
    @GetMapping("/all")
    public List<ProgressLog> getAllProgressLogs() {
        return progressLogRepository.findAll();
    }

    // POST İsteği: Yeni bir tartım/ölçüm yapıldığında bunu veritabanına ekler
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addProgressLog(@RequestBody ProgressLog newLog) {
        
        Map<String, Object> response = new HashMap<>();

        // 1. İş Mantığı: Eğer tarihsiz gönderildiyse bugünün tarihini ata
        if (newLog.getDate() == null) {
            newLog.setDate(LocalDate.now());
        }
        
        // 2. Veritabanına kaydet
        ProgressLog savedLog = progressLogRepository.save(newLog);

        // 3. Başarı mesajını o anki seçili dile göre properties dosyasından çek
        String successMessage = messageSource.getMessage(
                "progress.save.success", 
                null, 
                LocaleContextHolder.getLocale()
        );

        // 4. Yanıt paketini oluştur ve React'e gönder
        response.put("message", successMessage);
        response.put("data", savedLog);

        return ResponseEntity.ok(response);
    }

    // YENİ EKLENEN SİLME İŞLEMİ (DELETE)
    // Örn: http://localhost:8080/api/progress/delete/5
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProgressLog(@PathVariable Long id) {
        try {
            // ID'ye göre kaydı veritabanından kalıcı olarak sil
            progressLogRepository.deleteById(id);
            
            // Başarı mesajı dön
            Map<String, String> response = new HashMap<>();
            response.put("message", "Kayıt başarıyla silindi!");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Silme işlemi başarısız: " + e.getMessage());
        }
    }
}