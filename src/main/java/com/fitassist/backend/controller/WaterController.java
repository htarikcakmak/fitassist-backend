package com.fitassist.backend.controller;

import com.fitassist.backend.model.WaterLog;
import com.fitassist.backend.repository.WaterLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController // Bu sınıfın bir API iletişim noktası olduğunu belirtir
@RequestMapping("/api/water") // Bu sınıfa gelecek isteklerin ana URL'sini belirler
@CrossOrigin(origins = "http://localhost:5173") // React uygulamamızın (Vite) erişimine izin verir
public class WaterController {

    @Autowired
    private WaterLogRepository waterLogRepository;

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
    public WaterLog updateWaterLog(@RequestBody WaterLog updatedLog) {
        LocalDate today = LocalDate.now();
        
        // Bugünün kaydını bul, yoksa boş bir kayıt oluştur
        WaterLog existingLog = waterLogRepository.findByDate(today).orElse(new WaterLog());
        
        // React'ten gelen yeni verilerle kaydı güncelle
        existingLog.setDate(today);
        existingLog.setConsumedAmount(updatedLog.getConsumedAmount());
        existingLog.setTargetAmount(updatedLog.getTargetAmount());
        
        // Veritabanına kaydet ve güncel halini React'e geri gönder
        return waterLogRepository.save(existingLog);
    }
}