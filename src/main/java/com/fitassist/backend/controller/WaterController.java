package com.fitassist.backend.controller;

import com.fitassist.backend.model.WaterRecord;
import com.fitassist.backend.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/water")
@CrossOrigin(origins = "*") 
public class WaterController {

    @Autowired
    private WaterService waterService;

    @GetMapping("/today")
    public ResponseEntity<?> getTodaysWater(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        return ResponseEntity.ok(waterService.getTodaysWater(principal.getName()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addWater(@RequestBody WaterRecord record, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            waterService.addWater(record, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Su kaydı başarıyla eklendi!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Su eklenemedi: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWater(@PathVariable Long id, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            waterService.deleteWater(id, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Su kaydı başarıyla silindi!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Kayıt silinemedi: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllWater(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        return ResponseEntity.ok(waterService.getAllWaterRecords(principal.getName()));
    }
}
