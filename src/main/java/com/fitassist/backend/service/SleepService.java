package com.fitassist.backend.service;

import com.fitassist.backend.model.SleepRecord;
import com.fitassist.backend.model.User;
import com.fitassist.backend.repository.SleepRepository;
import com.fitassist.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SleepService {

    @Autowired
    private SleepRepository sleepRepository;

    // YENİ: Kullanıcıyı bulabilmemiz için UserRepository'i dahil ediyoruz
    @Autowired
    private UserRepository userRepository;

    // 1. Sadece token sahibi kullanıcının uyku kayıtlarını getir
    public List<SleepRecord> getAllSleepRecords(String userEmail) {
        return sleepRepository.findByUserEmail(userEmail);
    }

    // 2. Yeni uyku kaydına kullanıcı mührünü vur ve ekle
    public SleepRecord addSleepRecord(SleepRecord record, String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
                
        record.setUser(currentUser); // Kaydı yapan kullanıcıyı set ediyoruz
        return sleepRepository.save(record);
    }

    // 3. İstenilen uyku kaydını güvenlik kontrolünden geçirerek sil
    public void deleteSleepRecord(Long id, String userEmail) {
        SleepRecord record = sleepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Uyku kaydı bulunamadı!"));
                
        // Güvenlik: Silinmek istenen kayıt gerçekten giriş yapan kişiye mi ait?
        if (!record.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Bu kaydı silme yetkiniz yok!");
        }
        
        sleepRepository.delete(record);
    }
}