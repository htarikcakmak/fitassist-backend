package com.fitassist.backend.controller;

import com.fitassist.backend.model.WorkoutRecord;
import com.fitassist.backend.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/workout")
@CrossOrigin(origins = "*") 
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/today")
    public ResponseEntity<?> getTodaysWorkouts(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        return ResponseEntity.ok(workoutService.getTodaysWorkouts(principal.getName()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addWorkout(@RequestBody WorkoutRecord record, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            workoutService.addWorkout(record, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Antrenman başarıyla eklendi!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Antrenman eklenemedi: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        try {
            workoutService.deleteWorkout(id, principal.getName());
            return ResponseEntity.ok(Map.of("message", "Antrenman başarıyla silindi!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Antrenman silinemedi: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllWorkouts(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body(Map.of("message", "Oturum süresi doldu."));
        return ResponseEntity.ok(workoutService.getAllWorkouts(principal.getName()));
    }
}