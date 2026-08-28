package com.fitassist.backend.controller;

import com.fitassist.backend.model.NutritionLog;
import com.fitassist.backend.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = "*") 
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    @GetMapping("/today")
    public ResponseEntity<?> getTodaysNutrition(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        return ResponseEntity.ok(nutritionService.getTodaysNutrition(principal.getName()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNutrition(@RequestBody NutritionLog nutritionLog, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            nutritionService.addNutrition(nutritionLog, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Besin başarıyla eklendi!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Besin eklenirken hata oluştu: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNutrition(@PathVariable Long id, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            nutritionService.deleteNutrition(id, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Besin başarıyla silindi!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Besin silinirken hata oluştu: " + e.getMessage()));
        }
    }
}