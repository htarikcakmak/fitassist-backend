package com.fitassist.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "water_records")
@Data // Lombok: Getter ve Setter'ları otomatik oluşturur
public class WaterRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer amount; // Tüketilen su miktarı (Örn: 250 ml veya 1 bardak)

    @Column(nullable = false)
    private LocalDate date;

    // Veritabanına kayıt yapılmadan hemen önce tarihi otomatik atar
    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDate.now();
        }
    }
}