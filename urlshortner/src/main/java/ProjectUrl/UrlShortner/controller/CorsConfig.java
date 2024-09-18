package ProjectUrl.UrlShortner.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class CorsConfig {

    private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                logger.info("Iniciando configuração de CORS");
                registry.addMapping("/**") // Isso permitirá CORS para todas as rotas
                        .allowedOrigins(
                                "http://localhost:8081",
                                "http://localhost:4200",
                                "http://localhost:8080",
                                "http://54.232.58.42",
                                "http://54.232.58.42:8080",
                                "http://54.232.58.42:80",
                                "http://54.232.58.42:3000",
                                "https://54.232.58.42",
                                "http://54.232.58.42/api"
                        )
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
                        .allowedHeaders("*")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Content-Length", "Content-Range")
                        .allowCredentials(true)
                        .maxAge(3600); // 1 hora de cache para respostas pre-flight
                logger.info("Configuração de CORS concluída com sucesso");
            }
        };
    }
}