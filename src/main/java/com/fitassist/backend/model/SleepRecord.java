package com.fitassist.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "sleep_records")
@Data // Lombok kütüphanesi otomatik olarak getter ve setter oluşturur
public class SleepRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double hours; // Uyunan saat (Örn: 7.5)

    @Column(nullable = false)
    private LocalDate date; // Kaydın tarihi
}