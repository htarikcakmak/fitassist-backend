package com.fitassist.backend.config; // Kendi paket isminle değiştir

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Güvenlik ayarları: Tüm cihazlardan, tüm metodlardan gelen isteklere izin veriyoruz
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // React, Mobil, Localhost hepsine kapıyı açar
        config.addAllowedHeader("*");        // Bütün veri tiplerini kabul eder
        config.addAllowedMethod("*");        // GET, POST, PUT, DELETE hepsine izin verir
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}