package com.mindbridge.gateway.config;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
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
        Set<String> allowedOrigins = new LinkedHashSet<>();
        allowedOrigins.add("http://localhost:5173");
        allowedOrigins.add("https://front-mind-bridge.vercel.app");
        allowedOrigins.add("https://videochat-sfu-app.azurewebsites.net");
        allowedOrigins.add("https://gateway-service.orangebay-0b927206.eastus.azurecontainerapps.io");

        String envOrigins = System.getenv("ALLOWED_ORIGINS");
        if (envOrigins != null && !envOrigins.isBlank()) {
            Arrays.stream(envOrigins.split(","))
                    .map(String::trim)
                    .filter(origin -> !origin.isEmpty())
                    .forEach(allowedOrigins::add);
        }

        allowedOrigins.forEach(corsConfig::addAllowedOrigin);
        corsConfig.addAllowedOriginPattern("https://*.vercel.app");
        corsConfig.addAllowedOriginPattern("http://localhost:*");
        corsConfig.addAllowedMethod("*");                       // Todos los métodos
        corsConfig.addAllowedHeader("*");                       // Todas las cabeceras
        corsConfig.setAllowCredentials(true);                   // Si usas cookies/token

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);    // Aplica a todas las rutas

        return new CorsWebFilter(source);
    }
}
