package com.amazingapps.restaurantfinder.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExecutionConflictException extends RuntimeException {

    private final ErrorType errorType;
    private final HttpStatus status;

    public ExecutionConflictException(String message) {
        this(ErrorType.CONFLICT, HttpStatus.CONFLICT, message, null);
    }

    public ExecutionConflictException(ErrorType errorType, HttpStatus status, String message, Throwable error) {
        super(message, error);
        this.errorType = errorType;
        this.status = status;
    }
}