package com.fitassist.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String password;

    // YENİ: Null hatasını önlemek için başlangıç değerleri atıyoruz (0)
    private Integer height = 0;
    private Integer weight = 0;
    private Integer age = 0;
    private String goal = "Vücut Kompozisyonu";
    
    @Lob
    @Column(columnDefinition = "TEXT")
    // YENİ: Başlangıçta boş resim yerine varsayılan avatar linki veriyoruz
    private String imageUrl = "https://api.dicebear.com/7.x/avataaars/svg?seed=User&backgroundColor=transparent";

    private String language = "tr"; 
    private String themeBg = "#d8c97f"; 
    private String themePrimary = "#6a9433";

    // Constructor (Boş kurucu metod zorunludur)
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

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getThemeBg() { return themeBg; }
    public void setThemeBg(String themeBg) { this.themeBg = themeBg; }

    public String getThemePrimary() { return themePrimary; }
    public void setThemePrimary(String themePrimary) { this.themePrimary = themePrimary; }
}