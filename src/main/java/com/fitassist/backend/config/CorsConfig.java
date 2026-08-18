package com.fitassist.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Uygulamadaki tüm sayfalara (/**) dışarıdan gelecek isteklere izin veriyoruz
        registry.addMapping("/**")
                .allowedOrigins("*") // Tüm kaynaklara (mobil cihazlar, farklı siteler) izin ver
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // İzin verilen işlem türleri
                .allowedHeaders("*"); // Tüm veri başlıklarına izin ver
    }
}