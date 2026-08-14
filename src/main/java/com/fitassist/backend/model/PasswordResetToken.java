package com.fitassist.backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class PasswordResetToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tek kullanımlık rastgele üretilecek anahtarımız
    private String token;

    // Bu anahtarın hangi kullanıcıya ait olduğunu bağlıyoruz
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    // Anahtarın son kullanma tarihi
    private Date expiryDate;

    // --- GETTER VE SETTER METODLARI ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}