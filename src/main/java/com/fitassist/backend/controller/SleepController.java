package com.fitassist.backend.controller;

import com.fitassist.backend.model.SleepRecord;
import com.fitassist.backend.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sleep")
@CrossOrigin(origins = "*") // React'ten gelen isteklere izin ver
public class SleepController {

    @Autowired
    private SleepService sleepService;

    // DİKKAT: "Principal principal" ekledik. Kimin istek attığını bize otomatik söyler.
    @GetMapping("/all")
    public ResponseEntity<List<SleepRecord>> getAll(Principal principal) {
        return ResponseEntity.ok(sleepService.getAllSleepRecords(principal.getName()));
    }

    // React'ten gelen yeni kaydı al ve giriş yapan kullanıcının e-postasıyla servise yolla
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SleepRecord record, Principal principal) {
        try {
            sleepService.addSleepRecord(record, principal.getName());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Uyku kaydı başarıyla eklendi!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Kayıt eklenemedi: " + e.getMessage());
        }
    }

    // React'teki çöp kutusu ikonuna basıldığında gelen silme isteği
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        try {
            sleepService.deleteSleepRecord(id, principal.getName());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Kayıt başarıyla silindi!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Kayıt silinemedi: " + e.getMessage());
        }
    }
}