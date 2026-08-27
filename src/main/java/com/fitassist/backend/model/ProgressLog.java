package com.fitassist.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "progress_logs")
public class ProgressLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Double weight;
    private Double bodyFatPercentage;
    private Double muscleMass;

    // YENİ: Bu ölçümün hangi kullanıcıya ait olduğunu belirten mühür
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore // React'e JSON gönderirken döngüye girmeyi engeller
    private User user;
}