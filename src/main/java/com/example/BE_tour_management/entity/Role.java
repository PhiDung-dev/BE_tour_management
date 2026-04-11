package com.example.BE_tour_management.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Role {

    ADMIN("ADMIN"),
    STAFF("STAFF"),
    USER("USER")
    ;

    String role;
}
