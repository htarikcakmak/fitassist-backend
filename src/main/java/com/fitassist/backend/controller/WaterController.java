package com.fitassist.backend.controller;

import com.fitassist.backend.model.WaterRecord;
import com.fitassist.backend.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/water")
@CrossOrigin(origins = "*") // React üzerinden gelen isteklere izin ver
public class WaterController {

    @Autowired
    private WaterService waterService;

    // React'in açılışta çağıracağı "Bugünün su kayıtlarını getir" ucu
    @GetMapping("/today")
    public ResponseEntity<List<WaterRecord>> getTodaysWater() {
        return ResponseEntity.ok(waterService.getTodaysWater());
    }

    // React'ten gelen yeni su ekleme isteği
    @PostMapping("/add")
    public ResponseEntity<?> addWater(@RequestBody WaterRecord record) {
        try {
            waterService.addWater(record);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Su kaydı başarıyla eklendi!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Su eklenemedi: " + e.getMessage());
        }
    }

    // React'teki çöp kutusu ikonuna basıldığında gelen silme isteği
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWater(@PathVariable Long id) {
        try {
            waterService.deleteWater(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Su kaydı başarıyla silindi!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Kayıt silinemedi: " + e.getMessage());
        }
    }

    // React'in haftalık grafiği çizmek için çağıracağı uç nokta
    @GetMapping("/all")
    public ResponseEntity<List<WaterRecord>> getAllWater() {
        return ResponseEntity.ok(waterService.getAllWaterRecords());
    }
}