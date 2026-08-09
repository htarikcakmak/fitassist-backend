package com.fitassist.backend.controller;

import com.fitassist.backend.model.WaterLog;
import com.fitassist.backend.repository.WaterLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController // Bu sınıfın bir API iletişim noktası olduğunu belirtir
@RequestMapping("/api/water") // Bu sınıfa gelecek isteklerin ana URL'sini belirler
@CrossOrigin(origins = "http://localhost:5173") // React uygulamamızın erişimine izin verir
public class WaterController {

    @Autowired
    private WaterLogRepository waterLogRepository;

    @Autowired
    private MessageSource messageSource;

    // GET İsteği: Uygulama açıldığında bugünün su verilerini getirir
    @GetMapping("/today")
    public WaterLog getTodayWaterLog() {
        LocalDate today = LocalDate.now();
        
        return waterLogRepository.findByDate(today).orElseGet(() -> {
            WaterLog newLog = new WaterLog();
            newLog.setDate(today);
            newLog.setConsumedAmount(0);
            newLog.setTargetAmount(3000);
            return waterLogRepository.save(newLog);
        });
    }

    // POST İsteği: Yeni su eklendiğinde veya hedef değiştiğinde çalışır
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateWaterLog(@RequestBody WaterLog updatedLog) {
        
        Map<String, Object> response = new HashMap<>();
        LocalDate today = LocalDate.now();
        
        // Hata kontrolü (try-catch) kaldırıldı! Sadece başarılı senaryoyu yazıyoruz.
        
        // 1. Veritabanı işlemleri
        WaterLog existingLog = waterLogRepository.findByDate(today).orElse(new WaterLog());
        existingLog.setDate(today);
        existingLog.setConsumedAmount(updatedLog.getConsumedAmount());
        existingLog.setTargetAmount(updatedLog.getTargetAmount());
        WaterLog savedLog = waterLogRepository.save(existingLog);

        // 2. Başarı mesajını o anki seçili dile göre çek
        String successMessage = messageSource.getMessage(
                "water.update.success", 
                null, 
                LocaleContextHolder.getLocale()
        );

        // 3. Yanıt paketini oluştur ve React'e gönder
        response.put("message", successMessage);
        response.put("data", savedLog);

        return ResponseEntity.ok(response);
        
        // Eğer burada veritabanı kaynaklı bir hata olursa, 
        // GlobalExceptionHandler devreye girip React'e otomatik olarak hata paketini dönecek.
    }
}