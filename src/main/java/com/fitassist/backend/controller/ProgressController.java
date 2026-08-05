package com.fitassist.backend.controller;

import com.fitassist.backend.model.ProgressLog;
import com.fitassist.backend.repository.ProgressLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "http://localhost:5173")
public class ProgressController {

    @Autowired
    private ProgressLogRepository progressLogRepository;

    // GET İsteği: Grafiği çizmek için geçmişteki tüm gelişim kayıtlarını listeler
    @GetMapping("/all")
    public List<ProgressLog> getAllProgressLogs() {
        // Veritabanındaki tüm tartım kayıtlarını getirir
        return progressLogRepository.findAll();
    }

    // POST İsteği: Yeni bir tartım/ölçüm yapıldığında bunu veritabanına ekler
    @PostMapping("/add")
    public ProgressLog addProgressLog(@RequestBody ProgressLog newLog) {
        // Eğer tarihsiz gönderildiyse bugünün tarihini ata
        if (newLog.getDate() == null) {
            newLog.setDate(LocalDate.now());
        }
        
        // Veritabanına kaydet
        return progressLogRepository.save(newLog);
    }
}