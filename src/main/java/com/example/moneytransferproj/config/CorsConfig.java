package com.example.moneytransferproj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("#{'${allowedOrigins}'.split(', ')}")
    private List<String> allowedOrigins;

    @Value("#{'${allowedMethods}'.split(', ')}")
    private List<String> allowedMethods;

    @Value("${maxAge}")
    private Long maxAge;

    public CorsConfig() {

    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins.toArray(new String[0]))
                .allowedMethods(allowedMethods.toArray(new String[0]))
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(maxAge);
    }

}


