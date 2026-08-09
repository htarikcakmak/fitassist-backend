package com.fitassist.backend.config; // Lütfen kendi paket isminle değiştir

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class I18nConfig {

    // 1. İsteklerin başlığındaki (header) dil bilgisini yakalar ve belirler
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        // Eğer ön yüzden bir dil bilgisi gelmezse varsayılan olarak Türkçe (tr) kullanılsın
        resolver.setDefaultLocale(new Locale("tr")); 
        return resolver;
    }

    // 2. Mesajların hangi dosyalardan okunacağını ayarlar
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 'messages.properties' isimli dosyaları arar
        messageSource.setBasename("messages"); 
        // Türkçe karakter sorunu yaşamamak için UTF-8 formatını zorunlu kılarız
        messageSource.setDefaultEncoding("UTF-8"); 
        return messageSource;
    }
}
