package com.fitassist.backend.controller;

import com.fitassist.backend.model.NutritionLog;
import com.fitassist.backend.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = "*") // React (localhost:5173) üzerinden gelen isteklere izin verir
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    // React'in açılışta çağırdığı "Bugünün besinlerini getir" ucu
    @GetMapping("/today")
    public ResponseEntity<List<NutritionLog>> getTodaysNutrition() {
        List<NutritionLog> logs = nutritionService.getTodaysNutrition();
        return ResponseEntity.ok(logs);
    }

    // React'in "Öğüne Ekle" butonuna basıldığında çağırdığı uç
    @PostMapping("/add")
    public ResponseEntity<?> addNutrition(@RequestBody NutritionLog nutritionLog) {
        try {
            // Veriyi kaydet
            nutritionService.addNutrition(nutritionLog);
            
            // React tarafının beklediği JSON formatında başarı mesajını hazırla
            Map<String, String> response = new HashMap<>();
            response.put("message", "Besin başarıyla eklendi!");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Hata durumunda yine React'in yakalayabileceği JSON formatında bir hata dön
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Besin eklenirken bir hata oluştu: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
    }
    
    // Yanlış eklenen besini silmek için uç nokta
    // Örn: http://localhost:8080/api/nutrition/delete/5
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNutrition(@PathVariable Long id) {
        try {
            // Veritabanından sil
            nutritionService.deleteNutrition(id);
            
            // React'e başarı mesajı dön
            Map<String, String> response = new HashMap<>();
            response.put("message", "Besin başarıyla silindi!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Besin silinirken bir hata oluştu: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}