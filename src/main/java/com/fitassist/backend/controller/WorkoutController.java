package com.fitassist.backend.controller;

import com.fitassist.backend.model.WorkoutRecord;
import com.fitassist.backend.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workout")
@CrossOrigin(origins = "*") // React üzerinden gelecek (localhost:5173) isteklere engel olma
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    // React'in açılışta sayfaya basmak için çekeceği "Bugünün Antrenmanları" ucu
    @GetMapping("/today")
    public ResponseEntity<List<WorkoutRecord>> getTodaysWorkouts() {
        return ResponseEntity.ok(workoutService.getTodaysWorkouts());
    }

    // React'ten yeni bir hareket eklendiğinde çalışacak uç nokta
    @PostMapping("/add")
    public ResponseEntity<?> addWorkout(@RequestBody WorkoutRecord record) {
        try {
            workoutService.addWorkout(record);
            
            // React tarafında gösterilecek başarı mesajı (İngilizce standartlarına uygun)
            Map<String, String> response = new HashMap<>();
            response.put("message", "Workout record successfully added!");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding workout: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // React'te çöp kutusuna basıldığında çalışacak silme ucu
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id) {
        try {
            workoutService.deleteWorkout(id);
            
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
    public ResponseEntity<List<WorkoutRecord>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }
}