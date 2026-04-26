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
                Set<String> staffRole = Set.of(Role.STAFF.getRole());
                Account account = Account.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                Account account1 = Account.builder()
                        .username("staff1")
                        .password(passwordEncoder.encode("staff1"))
                        .roles(staffRole)
                        .build();
                Account account2 = Account.builder()
                        .username("staff2")
                        .password(passwordEncoder.encode("staff2"))
                        .roles(staffRole)
                        .build();
                Account account3 = Account.builder()
                        .username("staff3")
                        .password(passwordEncoder.encode("staff3"))
                        .roles(staffRole)
                        .build();
                accountRepository.save(account);
            }
        };
    }

}
