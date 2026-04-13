package com.example.BE_tour_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {

    String id;
    String username;
    String password;
    Set<String> roles;

}
