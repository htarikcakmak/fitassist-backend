package com.fitassist.backend.controller; // Kendi paket ismini buraya yaz

import com.fitassist.backend.model.SleepLog;
import com.fitassist.backend.repository.SleepLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sleep")
@CrossOrigin(origins = "http://localhost:3000") // React'in çalıştığı adresi buraya yaz (Vite kullanıyorsan 5173 olabilir)
public class SleepController {

    @Autowired
    private SleepLogRepository sleepLogRepository;

    // React sayfası ilk açıldığında geçmiş verileri çekmek için çalışır
    @GetMapping("/all")
    public List<SleepLog> getAllSleepLogs() {
        return sleepLogRepository.findAll();
    }

    // React'teki formdan gelen yeni uyku verisini kaydetmek için çalışır
    @PostMapping("/add")
    public SleepLog addSleepLog(@RequestBody SleepLog sleepLog) {
        return sleepLogRepository.save(sleepLog);
    }
}