package com.fitassist.backend.controller;

import com.fitassist.backend.model.SleepRecord;
import com.fitassist.backend.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sleep")
@CrossOrigin(origins = "*") 
public class SleepController {

    @Autowired
    private SleepService sleepService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        return ResponseEntity.ok(sleepService.getAllSleepRecords(principal.getName()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SleepRecord record, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            sleepService.addSleepRecord(record, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Uyku kaydı başarıyla eklendi!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Kayıt eklenemedi: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            sleepService.deleteSleepRecord(id, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Kayıt başarıyla silindi!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Kayıt silinemedi: " + e.getMessage()));
        }
    }
}
