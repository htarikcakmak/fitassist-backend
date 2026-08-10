package com.fitassist.backend.controller;

import com.fitassist.backend.model.SleepRecord;
import com.fitassist.backend.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sleep")
@CrossOrigin(origins = "*") // React'ten gelen isteklere izin ver
public class SleepController {

    @Autowired
    private SleepService sleepService;

    // Tüm kayıtları React'e gönder
    @GetMapping("/all")
    public ResponseEntity<List<SleepRecord>> getAll() {
        return ResponseEntity.ok(sleepService.getAllSleepRecords());
    }

    // React'ten gelen yeni kaydı al ve veritabanına ekle
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SleepRecord record) {
        try {
            sleepService.addSleepRecord(record);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Uyku kaydı başarıyla eklendi!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Kayıt eklenemedi: " + e.getMessage());
        }
    }

    // React'teki çöp kutusu ikonuna basıldığında gelen silme isteği
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            sleepService.deleteSleepRecord(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Kayıt başarıyla silindi!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Kayıt silinemedi: " + e.getMessage());
        }
    }
}