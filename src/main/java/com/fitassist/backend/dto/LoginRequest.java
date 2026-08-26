package com.fitassist.backend.dto; 

/**
 * Bu sınıf, giriş yaparken React'ten gelen sadece E-posta ve Şifre
 * bilgilerini karşılamak için oluşturulmuş özel bir veri taşıyıcısıdır.
 */
public class LoginRequest {
    private String email;
    private String password;

    // Boş Constructor (Spring Boot'un veriyi oluşturabilmesi için gereklidir)
    public LoginRequest() {}

    // Getter ve Setter Metotları
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