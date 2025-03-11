package com.cs.calendarback.config;

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

        // config.setAllowCredentials(true); // 쿠키 포함 요청 허용 (필요 없으면 생략 가능)
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addExposedHeader("*"); // 응답 헤더 노출
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
