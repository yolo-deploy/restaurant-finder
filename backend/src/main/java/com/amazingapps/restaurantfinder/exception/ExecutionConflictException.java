package com.amazingapps.restaurantfinder.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a conflict occurs during execution (e.g., duplicate data).
 * Contains error type and HTTP status.
 */
@Getter
public class ExecutionConflictException extends RuntimeException {

    private final ErrorType errorType;
    private final HttpStatus status;

    /**
     * Constructs a new ExecutionConflictException with default error type and status.
     * @param message error message
     */
    public ExecutionConflictException(String message) {
        this(ErrorType.CONFLICT, HttpStatus.CONFLICT, message, null);
    }

    /**
     * Constructs a new ExecutionConflictException with custom error type, status, message, and cause.
     * @param errorType type of error
     * @param status HTTP status
     * @param message error message
     * @param error underlying cause
     */
    public ExecutionConflictException(ErrorType errorType, HttpStatus status, String message, Throwable error) {
        super(message, error);
        this.errorType = errorType;
        this.status = status;
    }
}