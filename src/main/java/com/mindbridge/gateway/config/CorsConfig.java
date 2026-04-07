package com.mindbridge.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:5173");
        corsConfig.addAllowedOrigin("https://front-mind-bridge.vercel.app"); // Origen del frontend
        corsConfig.addAllowedMethod("*");                       // Todos los métodos
        corsConfig.addAllowedHeader("*");                       // Todas las cabeceras
        corsConfig.setAllowCredentials(true);                   // Si usas cookies/token

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);    // Aplica a todas las rutas

        return new CorsWebFilter(source);
    }
}