package com.fitassist.backend.service;

import com.fitassist.backend.model.WaterRecord;
import com.fitassist.backend.repository.WaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WaterService {

    @Autowired
    private WaterRepository waterRepository;

    // 1. Sadece bugüne ait su kayıtlarını getir
    public List<WaterRecord> getTodaysWater() {
        return waterRepository.findByDate(LocalDate.now());
    }

    // 2. Yeni su kaydı ekle
    public WaterRecord addWater(WaterRecord record) {
        return waterRepository.save(record);
    }

    // 3. Su kaydını ID'sine göre sil
    public void deleteWater(Long id) {
        waterRepository.deleteById(id);
    }

    // Tüm geçmiş su kayıtlarını getirir (Haftalık grafik için)
    public List<WaterRecord> getAllWaterRecords() {
        return waterRepository.findAll();
    }
}