package com.example.BE_tour_management.service;

import com.example.BE_tour_management.entity.Account;
import com.example.BE_tour_management.entity.User;
import com.example.BE_tour_management.repository.AccountRepository;
import com.example.BE_tour_management.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityService {

    AccountRepository accountRepository;
    UserRepository userRepository;

    public boolean isOwnerAccount(String id, String username) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(value -> value.getUsername().equalsIgnoreCase(username)).orElse(false);
    }

    public boolean isOwnerUser(String id, String username) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value->value.getAccount().getUsername().equalsIgnoreCase(username)).orElse(false);
    }

}
