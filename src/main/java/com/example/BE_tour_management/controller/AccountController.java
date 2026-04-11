package com.example.BE_tour_management.controller;

import com.example.BE_tour_management.dto.ApiResponse;
import com.example.BE_tour_management.dto.request.AccountCreateRequest;
import com.example.BE_tour_management.dto.request.AccountUpdateRequest;
import com.example.BE_tour_management.dto.response.AccountResponse;
import com.example.BE_tour_management.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @PostMapping
    public ApiResponse<AccountResponse> createAccount(@RequestBody AccountCreateRequest request) {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.createAccount(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<AccountResponse>> readAccounts() {
        return ApiResponse.<List<AccountResponse>>builder()
                .result(accountService.readAccounts())
                .build();
    }

    @GetMapping("/{accountId}")
    public ApiResponse<AccountResponse> readAccount(@PathVariable("accountId") String id) {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.readAccount(id))
                .build();
    }

    @PutMapping("/{accountId}")
    public ApiResponse<AccountResponse> updateAccount(@PathVariable("accountId") String id, @RequestBody AccountUpdateRequest request) {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.updateAccount(id, request))
                .build();
    }

    @DeleteMapping("/{accountId}")
    public ApiResponse<Void> deleteAccount(@PathVariable("accountId") String id) {
        accountService.deleteAccount(id);
        return ApiResponse.<Void>builder()
                .message("account has been deleted")
                .build();
    }
}
