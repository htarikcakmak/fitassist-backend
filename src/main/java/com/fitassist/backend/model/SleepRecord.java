package com.fitassist.backend.model; 

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "sleep_records") // Veritabanındaki tablonun adını belirledik
public class SleepRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // React'ten gelen "05 Ağu" veya "2026-08-05" şeklindeki tarihi tutacak
    private String date; 
    
    // 7.5 gibi küsuratlı saatleri tutabilmek için double kullanıyoruz
    private double hours; 

    // YENİ: Bu uyku kaydının hangi kullanıcıya ait olduğunu belirten ilişki
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore // DİKKAT: Verileri React'e gönderirken sonsuz döngüyü engeller
    private User user;

    // Boş Yapıcı (JPA için zorunludur)
    public SleepRecord() {}

    // --- GETTER VE SETTER METOTLARI ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getHours() { return hours; }
    public void setHours(double hours) { this.hours = hours; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}