package com.fitassist.backend.model;

import jakarta.persistence.*;

@Entity
public class WaterRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount; // Kaç ml su içildiği
    private String date;    // Hangi gün içildiği (Örn: 2026-08-12)

    public WaterRecord() {}

    // GETTER VE SETTER METODLARI
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}