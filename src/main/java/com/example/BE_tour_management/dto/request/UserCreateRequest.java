package com.example.BE_tour_management.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    String fullName;
    String email;
    String phoneNumber;
    String address;
    String accountId;

}
