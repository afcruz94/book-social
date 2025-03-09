package com.afcruz.book_network.handler;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleLockedAccountException(LockedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .errorCode(ErrorCodes.ACCOUNT_LOCKED.getCode())
                        .description(ErrorCodes.ACCOUNT_LOCKED.getDescription())
                        .error(exception.getMessage())
                        .datetime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleDisableAccountException(DisabledException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .errorCode(ErrorCodes.ACCOUNT_DISABLED.getCode())
                        .description(ErrorCodes.ACCOUNT_DISABLED.getDescription())
                        .error(exception.getMessage())
                        .datetime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .errorCode(ErrorCodes.BAD_CREDENTIALS.getCode())
                        .description(ErrorCodes.BAD_CREDENTIALS.getDescription())
                        .datetime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleMessagingException(MessagingException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .errorCode(ErrorCodes.NO_CODE.getCode())
                        .description(ErrorCodes.NO_CODE.getDescription())
                        .error(exception.getMessage())
                        .datetime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Set<String> errors = new HashSet<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            errors.add(error.getDefaultMessage());
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .errorCode(ErrorCodes.BAD_CREDENTIALS.getCode())
                        .description(ErrorCodes.BAD_CREDENTIALS.getDescription())
                        .validationErrors(errors)
                        .datetime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        exception.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .errorCode(ErrorCodes.NO_CODE.getCode())
                        .description("Internal error, please contact support line")
                        .error(exception.getMessage())
                        .datetime(LocalDateTime.now())
                        .build());
    }
}
