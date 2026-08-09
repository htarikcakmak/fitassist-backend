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
@CrossOrigin(origins = "http://localhost:5173") // React uygulamamızın (Vite) erişimine izin verir
public class WaterController {

    @Autowired
    private WaterLogRepository waterLogRepository;

    // Çeviri mesajlarını okumamızı sağlayan araç
    @Autowired
    private MessageSource messageSource;

    // GET İsteği: Uygulama açıldığında bugünün su verilerini React'e gönderir
    @GetMapping("/today")
    public WaterLog getTodayWaterLog() {
        LocalDate today = LocalDate.now();
        
        // Veritabanında bugünün kaydını ara. Varsa getir, yoksa yeni bir tane oluşturup kaydet.
        return waterLogRepository.findByDate(today).orElseGet(() -> {
            WaterLog newLog = new WaterLog();
            newLog.setDate(today);
            newLog.setConsumedAmount(0); // Başlangıçta içilen su 0
            newLog.setTargetAmount(3000); // Varsayılan hedef 3000ml
            return waterLogRepository.save(newLog);
        });
    }

    // POST İsteği: React'te "Su Ekle" veya "Hedefi Değiştir" yapıldığında çalışır
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateWaterLog(@RequestBody WaterLog updatedLog) {
        
        Map<String, Object> response = new HashMap<>();
        LocalDate today = LocalDate.now();
        
        try {
            // Bugünün kaydını bul, yoksa boş bir kayıt oluştur
            WaterLog existingLog = waterLogRepository.findByDate(today).orElse(new WaterLog());
            
            // React'ten gelen yeni verilerle kaydı güncelle
            existingLog.setDate(today);
            existingLog.setConsumedAmount(updatedLog.getConsumedAmount());
            existingLog.setTargetAmount(updatedLog.getTargetAmount());
            
            // Veritabanına kaydet
            WaterLog savedLog = waterLogRepository.save(existingLog);

            // Başarı mesajını o anki seçili dile göre çek
            String successMessage = messageSource.getMessage(
                    "water.update.success", 
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