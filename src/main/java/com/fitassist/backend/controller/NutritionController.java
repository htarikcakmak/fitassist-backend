package com.fitassist.backend.controller;

import com.fitassist.backend.model.NutritionLog;
import com.fitassist.backend.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = "*") 
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    // DİKKAT: Principal eklenerek kimin istek attığı tespit ediliyor
    @GetMapping("/today")
    public ResponseEntity<List<NutritionLog>> getTodaysNutrition(Principal principal) {
        List<NutritionLog> logs = nutritionService.getTodaysNutrition(principal.getName());
        return ResponseEntity.ok(logs);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNutrition(@RequestBody NutritionLog nutritionLog, Principal principal) {
        try {
            nutritionService.addNutrition(nutritionLog, principal.getName());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Besin başarıyla eklendi!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Besin eklenirken bir hata oluştu: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNutrition(@PathVariable Long id, Principal principal) {
        try {
            nutritionService.deleteNutrition(id, principal.getName());
            
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