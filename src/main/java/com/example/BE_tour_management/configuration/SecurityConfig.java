package com.example.BE_tour_management.configuration;

import com.example.BE_tour_management.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.secret-key}")
    String secretKey;

    private final String[] PUBLIC_ENDPOINT = {"/accounts", "/auth/login", "/auth/introspect"};
    private final String[] ADMIN_ENDPOINT = {"/account", "/tours", "/users"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()
                .requestMatchers(HttpMethod.GET, ADMIN_ENDPOINT).hasAuthority("SCOPE_"+ Role.ADMIN)
                .requestMatchers(HttpMethod.DELETE, ADMIN_ENDPOINT).hasAuthority("SCOPE_"+Role.ADMIN)
                .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer
                .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
