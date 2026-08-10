package com.fitassist.backend.service;

import com.fitassist.backend.model.NutritionLog;
import com.fitassist.backend.repository.NutritionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service // Spring Boot'un bu dosyayı bir hizmet sınıfı olarak görmesini sağlar
public class NutritionService {

    @Autowired
    private NutritionLogRepository nutritionRepository;

    // 1. Yeni bir besin kaydını veritabanına kaydeder
    public NutritionLog addNutrition(NutritionLog nutritionLog) {
        return nutritionRepository.save(nutritionLog);
    }

    // 2. Sistem saatine göre "Bugün" olan kayıtları veritabanından çeker
    public List<NutritionLog> getTodaysNutrition() {
        LocalDate today = LocalDate.now();
        return nutritionRepository.findByDate(today);
    }

    // 3. İstenilen besin kaydını ID'sine göre siler
    public void deleteNutrition(Long id) {
        nutritionRepository.deleteById(id);
    }
}