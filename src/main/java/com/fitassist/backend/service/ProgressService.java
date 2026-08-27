package com.fitassist.backend.service;

import com.fitassist.backend.model.ProgressLog;
import com.fitassist.backend.model.User;
import com.fitassist.backend.repository.ProgressLogRepository;
import com.fitassist.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service // Sınıfımızı Spring Boot'a bir hizmet (Service) olarak tanıtıyoruz
public class ProgressService {

    @Autowired
    private ProgressLogRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. Sadece token sahibi kullanıcının tüm ölçümlerini getirir
    public List<ProgressLog> getAllProgress(String userEmail) {
        return progressRepository.findByUserEmail(userEmail);
    }

    // 2. Yeni ölçüme kullanıcı mührünü vurur ve tarihsizse bugün atayarak kaydeder
    public ProgressLog addProgress(ProgressLog log, String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        if (log.getDate() == null) {
            log.setDate(LocalDate.now());
        }

        log.setUser(currentUser); // Ölçümü kullanıcıya bağladık
        return progressRepository.save(log);
    }

    // 3. Güvenlik: Silinmek istenen ölçüm gerçekten bu kişiye mi ait?
    public void deleteProgress(Long id, String userEmail) {
        ProgressLog log = progressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ölçüm kaydı bulunamadı!"));

        if (!log.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Bu kaydı silme yetkiniz yok!");
        }

        progressRepository.deleteById(id);
    }
}