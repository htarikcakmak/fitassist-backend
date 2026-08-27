package com.fitassist.backend.controller;

import com.fitassist.backend.model.ProgressLog;
import com.fitassist.backend.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*") // '*' tüm originlere (uygulama portlarına) izin verir, güvenlidir
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    // DİKKAT: Principal ile sadece giriş yapanın verisini çekiyoruz
    @GetMapping("/all")
    public ResponseEntity<List<ProgressLog>> getAllProgressLogs(Principal principal) {
        return ResponseEntity.ok(progressService.getAllProgress(principal.getName()));
    }

    // React'ten gelen ölçümü, principal içindeki e-posta ile servise iletiyoruz
    @PostMapping("/add")
    public ResponseEntity<?> addProgressLog(@RequestBody ProgressLog newLog, Principal principal) {
        try {
            ProgressLog savedLog = progressService.addProgress(newLog, principal.getName());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Kayıt başarıyla eklendi!");
            response.put("data", savedLog);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Kayıt eklenemedi: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // React'teki çöp kutusu ikonuna basıldığında gelen güvenlikli silme isteği
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProgressLog(@PathVariable Long id, Principal principal) {
        try {
            progressService.deleteProgress(id, principal.getName());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Kayıt başarıyla silindi!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Silme işlemi başarısız: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}