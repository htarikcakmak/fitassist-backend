package com.fitassist.backend.service;

import com.fitassist.backend.model.User;
import com.fitassist.backend.model.WorkoutRecord;
import com.fitassist.backend.repository.UserRepository;
import com.fitassist.backend.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. Sadece giriş yapan kullanıcının bugünün antrenman hareketlerini listele
    public List<WorkoutRecord> getTodaysWorkouts(String userEmail) {
        String today = LocalDate.now().toString(); // Örn: "2026-08-27"
        return workoutRepository.findByUserEmailAndDate(userEmail, today);
    }

    // 2. Yeni bir antrenman hareketini giriş yapan kişiye bağla ve kaydet
    public WorkoutRecord addWorkout(WorkoutRecord record, String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        record.setUser(currentUser); // Hareketi kullanıcıya mühürledik
        return workoutRepository.save(record);
    }

    // 3. Güvenlik: Silinmek istenen hareket gerçekten bu kişiye mi ait?
    public void deleteWorkout(Long id, String userEmail) {
        WorkoutRecord record = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Antrenman kaydı bulunamadı!"));

        if (!record.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Bu kaydı silme yetkiniz yok!");
        }

        workoutRepository.deleteById(id);
    }

    // 4. Tüm geçmiş antrenman kayıtlarını sadece giriş yapan kişi için getirir (Geçmiş sekmesi için)
    public List<WorkoutRecord> getAllWorkouts(String userEmail) {
        return workoutRepository.findByUserEmail(userEmail);
    }
}