package com.fitassist.backend.controller;

import com.fitassist.backend.model.ProgressLog;
import com.fitassist.backend.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*") 
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProgressLogs(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        return ResponseEntity.ok(progressService.getAllProgress(principal.getName()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProgressLog(@RequestBody ProgressLog newLog, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            ProgressLog savedLog = progressService.addProgress(newLog, principal.getName());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Kayıt başarıyla eklendi!");
            response.put("data", savedLog);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Kayıt eklenemedi: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProgressLog(@PathVariable Long id, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            progressService.deleteProgress(id, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Kayıt başarıyla silindi!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Silme işlemi başarısız: " + e.getMessage()));
        }
    }
}