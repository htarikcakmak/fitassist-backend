package com.fitassist.backend.dto;

/**
 * Bu sınıf, kayıt olurken React'ten gelen Ad, E-posta ve Şifre
 * bilgilerini hatasız bir şekilde karşılamak için oluşturulmuştur.
 */
public class RegisterRequest {
    private String name;
    private String email;
    private String password;

    // Spring Boot'un JSON verisini nesneye çevirebilmesi için boş kurucu (constructor)
    public RegisterRequest() {}

    // Getter ve Setter Metotları
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}