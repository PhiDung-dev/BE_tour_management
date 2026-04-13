package com.example.BE_tour_management.configuration;

import com.example.BE_tour_management.entity.Account;
import com.example.BE_tour_management.entity.Role;
import com.example.BE_tour_management.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository) {
        return args -> {
            if(!accountRepository.existsByUsername("admin")) {
                Set<String> roles = Set.of(Role.ADMIN.getRole());
                Account account = Account.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                accountRepository.save(account);
            }
        };
    }

}
