package com.fitassist.backend.service;

import com.fitassist.backend.model.User;
import com.fitassist.backend.model.WaterRecord;
import com.fitassist.backend.repository.UserRepository;
import com.fitassist.backend.repository.WaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WaterService {

    @Autowired
    private WaterRepository waterRepository;

    @Autowired
    private UserRepository userRepository;

    // Sadece token sahibi kullanıcının bugünkü suyunu getirir
    public List<WaterRecord> getTodaysWater(String userEmail) {
        String today = LocalDate.now().toString();
        return waterRepository.findByUserEmailAndDate(userEmail, today);
    }

    // Suyu kaydederken arka planda asıl sahibini (User) bulup eşitler
    public void addWater(WaterRecord record, String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
            
        record.setUser(currentUser); // Kaydı mühürlüyoruz
        waterRepository.save(record);
    }

    // Güvenlik: Silinmek istenen kayıt gerçekten bu kullanıcıya mı ait?
    public void deleteWater(Long id, String userEmail) {
        WaterRecord record = waterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Kayıt bulunamadı!"));
            
        if (!record.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Bu kaydı silme yetkiniz yok!");
        }
        waterRepository.delete(record);
    }

    // Sadece token sahibi kullanıcının tüm sularını getirir
    public List<WaterRecord> getAllWaterRecords(String userEmail) {
        return waterRepository.findByUserEmail(userEmail);
    }
}