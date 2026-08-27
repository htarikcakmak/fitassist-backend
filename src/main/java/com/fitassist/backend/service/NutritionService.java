package com.fitassist.backend.service;

import com.fitassist.backend.model.NutritionLog;
import com.fitassist.backend.model.User;
import com.fitassist.backend.repository.NutritionLogRepository;
import com.fitassist.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NutritionService {

    @Autowired
    private NutritionLogRepository nutritionRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. Yeni besin kaydını giriş yapan kullanıcıya bağlayarak kaydet
    public NutritionLog addNutrition(NutritionLog nutritionLog, String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
                
        nutritionLog.setUser(currentUser); // Besini kullanıcıya mühürle
        return nutritionRepository.save(nutritionLog);
    }

    // 2. Sadece giriş yapan kişinin "Bugün" olan kayıtlarını çek
    public List<NutritionLog> getTodaysNutrition(String userEmail) {
        LocalDate today = LocalDate.now();
        return nutritionRepository.findByUserEmailAndDate(userEmail, today);
    }

    // 3. Güvenli Silme: Silinmek istenen besin gerçekten bu kişiye mi ait?
    public void deleteNutrition(Long id, String userEmail) {
        NutritionLog log = nutritionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Besin kaydı bulunamadı!"));
                
        if (!log.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Bu kaydı silme yetkiniz yok!");
        }
        
        nutritionRepository.deleteById(id);
    }
}