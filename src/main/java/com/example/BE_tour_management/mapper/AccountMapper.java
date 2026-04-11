package com.example.BE_tour_management.mapper;

import com.example.BE_tour_management.dto.request.AccountCreateRequest;
import com.example.BE_tour_management.dto.request.AccountUpdateRequest;
import com.example.BE_tour_management.dto.response.AccountResponse;
import com.example.BE_tour_management.entity.Account;
import com.example.BE_tour_management.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toAccount(AccountCreateRequest request);
    @Mapping(target = "role", ignore = true)
    AccountResponse toAccountResponse(Account account);
    List<AccountResponse> toAccountResponseList(List<Account> accounts);
    void updateAccount(@MappingTarget Account account, AccountUpdateRequest request);

    default String map(Role role) {
        return role.getRole();
    }
}
