package com.fitassist.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    // YENİ EKLENEN PROFİL ALANLARI
    private Integer height;
    private Integer weight;
    private Integer age;
    private String goal;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl;

    // Constructor'lar (Boş kurucu metod zorunludur)
    public User() {}

    // GETTER VE SETTER METODLARI
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    private String language = "tr"; 
    private String themeBg = "#d8c97f"; 
    private String themePrimary = "#6a9433";
    // --- YENİ EKLENEN AYARLAR İÇİN GETTER VE SETTER METODLARI ---

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getThemeBg() {
        return themeBg;
    }

    public void setThemeBg(String themeBg) {
        this.themeBg = themeBg;
    }

    public String getThemePrimary() {
        return themePrimary;
    }

    public void setThemePrimary(String themePrimary) {
        this.themePrimary = themePrimary;
    }
}