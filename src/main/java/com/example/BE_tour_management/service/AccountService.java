package com.example.BE_tour_management.service;

import com.example.BE_tour_management.dto.request.AccountCreateRequest;
import com.example.BE_tour_management.dto.request.AccountUpdateRequest;
import com.example.BE_tour_management.dto.response.AccountResponse;
import com.example.BE_tour_management.entity.Account;
import com.example.BE_tour_management.exception.AppException;
import com.example.BE_tour_management.exception.ErrorCode;
import com.example.BE_tour_management.mapper.AccountMapper;
import com.example.BE_tour_management.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;
    AccountMapper accountMapper;

    public AccountResponse createAccount(AccountCreateRequest request) {
        if(accountRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.ACCOUNT_EXISTED);
        }
        Account account = accountMapper.toAccount(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    public List<AccountResponse> readAccounts() {
        return accountMapper.toAccountResponseList(accountRepository.findAll());
    }

    public AccountResponse readAccount(String id) {
        return accountMapper.toAccountResponse(accountRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND)));
    }

    public AccountResponse updateAccount(String id, AccountUpdateRequest request) {
        Account account = accountRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        accountMapper.updateAccount(account, request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    public void deleteAccount(String id) {
        if(!accountRepository.existsById(id)) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        accountRepository.deleteById(id);
    }
}
