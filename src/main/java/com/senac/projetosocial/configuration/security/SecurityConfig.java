package com.senac.projetosocial.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senac.projetosocial.exceptions.RestExceptionHandler;
import com.senac.projetosocial.interception.filter.JWTAuthenticationFilter;
import com.senac.projetosocial.jwt.TokenValidation;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.*;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    private final RestExceptionHandler restExceptionHandler;
    private final ObjectMapper objectMapper;
    private final TokenValidation tokenValidation;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Arrays.asList("*"));
                            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                            config.setAllowedHeaders(Arrays.asList("*"));
                            return config;
                        }))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilterBefore(new JWTAuthenticationFilter(restExceptionHandler, objectMapper, this.tokenValidation),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
