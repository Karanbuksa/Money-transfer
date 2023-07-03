package com.example.moneytransferproj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    private static String[] allowedOrigins;
    private static String[] allowedMethods;
    private static Long maxAge;

    public CorsConfig(Environment env) {
        allowedOrigins = env.getProperty("allowedOrigins", String[].class);
        allowedMethods = env.getProperty("allowedMethods", String[].class);
        maxAge = env.getProperty("maxAge", Long.class);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(maxAge);
    }

}


