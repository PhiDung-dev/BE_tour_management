package com.example.BE_tour_management.service;

import com.example.BE_tour_management.dto.request.AccountCreateRequest;
import com.example.BE_tour_management.dto.request.AccountUpdateRequest;
import com.example.BE_tour_management.dto.response.AccountResponse;
import com.example.BE_tour_management.entity.Account;
import com.example.BE_tour_management.entity.Role;
import com.example.BE_tour_management.exception.AppException;
import com.example.BE_tour_management.exception.ErrorCode;
import com.example.BE_tour_management.mapper.AccountMapper;
import com.example.BE_tour_management.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;

    public AccountResponse createAccount(AccountCreateRequest request) {
        if(accountRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.ACCOUNT_EXISTED);
        }
        Account account = accountMapper.toAccount(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<String> roles = new HashSet<>();
        roles.add(Role.USER.getRole());
        account.setRoles(roles);
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountResponse> readAccounts() {
        return accountMapper.toAccountResponseList(accountRepository.findAll());
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerAccount(#id, authentication.name)")
    public AccountResponse readAccount(String id) {
        return accountMapper.toAccountResponse(accountRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND)));
    }

    @PreAuthorize("@securityService.isOwnerAccount(#id, authentication.name)")
    public AccountResponse updateAccount(String id, AccountUpdateRequest request) {
        Account account = accountRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        accountMapper.updateAccount(account, request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    @PreAuthorize("@securityService.isOwnerAccount(#id, authentication.name)")
    public void deleteAccount(String id) {
        if(!accountRepository.existsById(id)) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        accountRepository.deleteById(id);
    }
}
