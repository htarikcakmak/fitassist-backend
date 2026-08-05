package com.fitassist.backend.controller;

import com.fitassist.backend.model.WorkoutLog;
import com.fitassist.backend.repository.WorkoutLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workout")
@CrossOrigin(origins = "http://localhost:5173")
public class WorkoutController {

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    // GET İsteği: Uygulama açıldığında bugünün kaydedilmiş antrenman verilerini getirir
    @GetMapping("/today")
    public List<WorkoutLog> getTodayWorkoutLogs() {
        LocalDate today = LocalDate.now();
        // Sadece bugünün tarihine sahip hareketleri (set, tekrar, ağırlık) getirir
        return workoutLogRepository.findByDate(today);
    }

    // POST İsteği: React'te bir hareketin seti onaylandığında (Tike basıldığında) çalışır
    @PostMapping("/add")
    public WorkoutLog addWorkoutLog(@RequestBody WorkoutLog newLog) {
        // Tarih kontrolü yap ve yoksa bugünü ata
        if (newLog.getDate() == null) {
            newLog.setDate(LocalDate.now());
        }
        
        // Yeni seti veritabanına kaydet
        return workoutLogRepository.save(newLog);
    }
}