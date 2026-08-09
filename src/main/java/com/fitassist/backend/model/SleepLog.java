package com.fitassist.backend.model; 

import jakarta.persistence.*;

@Entity
@Table(name = "sleep_logs")
public class SleepLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // React'ten gelen "05 Ağu" veya "05/08/2026" şeklindeki tarihi tutacak
    private String date; 
    
    // 7.5 gibi küsuratlı saatleri tutabilmek için double kullanıyoruz
    private double hours; 

    // Boş Yapıcı (JPA için zorunludur)
    public SleepLog() {
    }

    // --- GETTER VE SETTER METOTLARI ---
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}