package com.fitassist.backend.service;

import com.fitassist.backend.model.SleepRecord;
import com.fitassist.backend.repository.SleepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SleepService {

    @Autowired
    private SleepRepository sleepRepository;

    // 1. Tüm uyku kayıtlarını getir
    public List<SleepRecord> getAllSleepRecords() {
        return sleepRepository.findAll();
    }

    // 2. Yeni uyku kaydı ekle
    public SleepRecord addSleepRecord(SleepRecord record) {
        return sleepRepository.save(record);
    }

    // 3. İstenilen uyku kaydını ID'sine göre sil
    public void deleteSleepRecord(Long id) {
        sleepRepository.deleteById(id);
    }
}