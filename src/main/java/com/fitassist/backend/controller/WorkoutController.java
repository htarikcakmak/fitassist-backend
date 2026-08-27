package com.fitassist.backend.controller;

import com.fitassist.backend.model.WorkoutRecord;
import com.fitassist.backend.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workout")
@CrossOrigin(origins = "*") 
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    // DİKKAT: Principal ile sadece giriş yapanın verisini çekiyoruz
    @GetMapping("/today")
    public ResponseEntity<List<WorkoutRecord>> getTodaysWorkouts(Principal principal) {
        return ResponseEntity.ok(workoutService.getTodaysWorkouts(principal.getName()));
    }

    // React'ten gelen yeni hareketi, principal içindeki e-posta ile servise iletiyoruz
    @PostMapping("/add")
    public ResponseEntity<?> addWorkout(@RequestBody WorkoutRecord record, Principal principal) {
        try {
            workoutService.addWorkout(record, principal.getName());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Workout record successfully added!");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding workout: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // React'te çöp kutusuna basıldığında çalışacak güvenlikli silme ucu
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id, Principal principal) {
        try {
            workoutService.deleteWorkout(id, principal.getName());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Workout record deleted successfully!");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error deleting workout: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // React'in geçmiş antrenmanları listelemek için çağıracağı uç nokta
    @GetMapping("/all")
    public ResponseEntity<List<WorkoutRecord>> getAllWorkouts(Principal principal) {
        return ResponseEntity.ok(workoutService.getAllWorkouts(principal.getName()));
    }
}