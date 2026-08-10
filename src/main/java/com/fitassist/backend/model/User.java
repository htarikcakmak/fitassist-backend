package com.fitassist.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Bu sınıfın veritabanında bir tablo olacağını belirtir
@Table(name = "users") // Veritabanındaki tablonun adını "users" yapar
@Data // Lombok kütüphanesi (Getter, Setter metodlarını otomatik oluşturur)
public class User {

    @Id // Bu alanın benzersiz bir kimlik (Primary Key) olduğunu belirtir
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'nin 1, 2, 3 diye otomatik artmasını sağlar
    private Long id;

    @Column(nullable = false) // Bu alanın boş bırakılamayacağını (Zorunlu) belirtir
    private String name;

    @Column(nullable = false, unique = true) // E-posta zorunludur ve başka biri aynı e-postayı kullanamaz
    private String email;

    @Column(nullable = false)
    private String password; // İleride bu şifreyi güvenlik için şifreleyerek (hash) kaydedeceğiz

    // Fiziksel metrikler (Kayıt olurken girilmeyebileceği için nullable = true, yani boş olabilir diyoruz)
    private Integer age;
    private Double height;
    private Double weight;
    
    private String goal; // "Kilo Verme", "Kas Kazanımı" vb.
    
    private String imageUrl; // Profil resminin internet adresi veya dosya yolu
}