package com.example.BE_tour_management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {

    ACCOUNT_EXISTED(1001, "account existed"),
    ACCOUNT_NOT_FOUND(1002, "account not found"),
    USER_EXISTED(1003, "user existed"),
    USER_NOT_FOUND(1004, "user not found"),
    TOUR_EXISTED(1005, "tour existed"),
    TOUR_NOT_FOUND(1006, "tour not found"),
    OTHERS_ERROR(9999, "others error")
    ;

    int code;
    String message;
}
