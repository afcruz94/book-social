package com.afcruz.book_network.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCodes {
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "Not Implemented"),
    INCORRECT_PASSWORD(300, HttpStatus.BAD_REQUEST, "Password is incorrect"),
    PASSWORD_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "New password does not match"),
    ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "User Account locked"),
    ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "User Account disabled"),
    BAD_CREDENTIALS(304, HttpStatus.FORBIDDEN, "Login credentials are incorrect");

    private final int code;
    private final HttpStatus status;
    private final String description;

}
