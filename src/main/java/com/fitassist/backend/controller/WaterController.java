package com.fitassist.backend.controller;

import com.fitassist.backend.model.WaterRecord;
import com.fitassist.backend.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/water")
@CrossOrigin(origins = "*") 
public class WaterController {

    @Autowired
    private WaterService waterService;

    // DİKKAT: Metotlara "Principal principal" ekledik. Bu, JWT token'ı okuyup e-postayı verir.
    @GetMapping("/today")
    public ResponseEntity<List<WaterRecord>> getTodaysWater(Principal principal) {
        return ResponseEntity.ok(waterService.getTodaysWater(principal.getName()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addWater(@RequestBody WaterRecord record, Principal principal) {
        try {
            waterService.addWater(record, principal.getName());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Su kaydı başarıyla eklendi!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Su eklenemedi: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWater(@PathVariable Long id, Principal principal) {
        try {
            waterService.deleteWater(id, principal.getName());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Su kaydı başarıyla silindi!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Kayıt silinemedi: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<WaterRecord>> getAllWater(Principal principal) {
        return ResponseEntity.ok(waterService.getAllWaterRecords(principal.getName()));
    }
}