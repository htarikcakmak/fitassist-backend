package com.fitassist.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Şifreleme için kullanılacak gizli anahtar (Sadece sunucu bilir ve bellekte üretilir)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Token geçerlilik süresi (Şu an 24 saat olarak ayarlandı: 1000ms * 60sn * 60dk * 24sa)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // 1. Kullanıcı e-postasına (username) göre yeni bir Token üretir
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // 2. Token oluşturma işleminin arka plan mantığı
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // Token'ın kime ait olduğu (E-posta)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Veriliş zamanı
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Bitiş zamanı
                .signWith(SECRET_KEY) // Güvenli anahtar ile imzalanması
                .compact();
    }

    // 3. Gelen Token'ın içinden kullanıcı adını (E-posta) çözer ve çıkarır
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 4. Token'ın süresinin dolup dolmadığını kontrol eder
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 5. Gelen Token'ın doğru kullanıcıya ait olup olmadığını ve süresinin geçerliliğini doğrular
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Tarih bilgisini çıkarmak için yardımcı metod
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Token içindeki şifreli verilere erişmek için yardımcı metod
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Token'ı gizli anahtarımızla çözme işlemi
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}