package com.fitassist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // API kullandığımız için CSRF korumasını kapatıyoruz
            .cors(cors -> cors.configure(http)) // React'ten gelen isteklere (CORS) izin veriyoruz
            .authorizeHttpRequests(auth -> auth
                // SADECE Giriş Yap ve Kayıt Ol uçlarına biletsiz (tokensız) erişime izin veriyoruz
                .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                // Geri kalan TÜM isteklere yetkilendirme (Token) zorunluluğu getiriyoruz
                .anyRequest().authenticated()
            )
            // Oturum (Session) yönetimini kapatıyoruz çünkü her istekte Token kontrolü yapacağız (Stateless)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Yazdığımız JwtRequestFilter'ı, Spring'in standart şifre filtresinden hemen ÖNCE çalışması için ekliyoruz
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Şifreleri veritabanında güvenli tutmak için BCrypt algoritmasını tanımlıyoruz
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security'nin giriş yaparken kullanıcıyı doğrulayacak yöneticisini dışarı açıyoruz
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}