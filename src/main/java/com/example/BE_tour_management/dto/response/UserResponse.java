package com.example.BE_tour_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;
    String fullName;
    String address;
    String email;
    String phoneNumber;
    AccountResponse account;

}
