package com.fitassist.backend.controller;

import com.fitassist.backend.model.NutritionLog;
import com.fitassist.backend.repository.NutritionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = "http://localhost:5173")
public class NutritionController {

    @Autowired
    private NutritionLogRepository nutritionLogRepository;

    // GET İsteği: Uygulama açıldığında bugünün tüm besin kayıtlarını getirir
    @GetMapping("/today")
    public List<NutritionLog> getTodayNutritionLogs() {
        LocalDate today = LocalDate.now();
        // Bugünün tarihine sahip tüm öğünleri veritabanından bulup liste halinde döner
        return nutritionLogRepository.findByDate(today);
    }

    // POST İsteği: React üzerinden yeni bir besin (Yumurta, Tavuk Döner vb.) eklendiğinde çalışır
    @PostMapping("/add")
    public NutritionLog addNutritionLog(@RequestBody NutritionLog newLog) {
        // Eğer React tarafından tarih gönderilmediyse, bugünün tarihini otomatik ekle
        if (newLog.getDate() == null) {
            newLog.setDate(LocalDate.now());
        }
        
        // Gelen yeni besin verisini veritabanına kaydet ve kaydedilmiş halini geri dön
        return nutritionLogRepository.save(newLog);
    }
}