package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.dto.user.UserCreateRequest;
import com.amazingapps.restaurantfinder.dto.user.UserResponse;
import com.amazingapps.restaurantfinder.dto.user.UserUpdatePasswordRequest;
import com.amazingapps.restaurantfinder.security.AuthInterceptor;
import com.amazingapps.restaurantfinder.security.AuthRequired;
import com.amazingapps.restaurantfinder.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user-related endpoints.
 * Provides CRUD operations for user management.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@AuthRequired(AuthInterceptor.class)
@Tag(name = "user", description = "User endpoints")
public class UserRestController {

    private final UserService service;
    private final AuthInterceptor authInterceptor;

    /**
     * Retrieves the details of the currently authenticated user.
     * Returns a 200 OK response with user details if successful.
     * Returns a 401 Unauthorized response if the user is not authenticated.
     * Returns a 500 Internal Server Error response if an unexpected error occurs.
     * Returns a 503 Service Unavailable response if the service is temporarily unavailable.
     */
    @GetMapping("/find")
    @Operation(summary = "Finds the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "503", description = "Service unavailable"),
    })
    public ResponseEntity<UserResponse> find() {
        String userId = authInterceptor.getUserId();
        UserResponse response = service.find(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new user.
     * Returns a 201 Created response if the user is created successfully.
     * Returns a 400 Bad Request response if the request data is invalid.
     * Returns a 401 Unauthorized response if the user is not authenticated.
     * Returns a 500 Internal Server Error response if an unexpected error occurs.
     * Returns a 503 Service Unavailable response if the service is temporarily unavailable.
     */
    @PostMapping("/create")
    @Operation(summary = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "503", description = "Service unavailable"),
    })
    public ResponseEntity<Void> create(@Valid @RequestBody UserCreateRequest request) {
        service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Updates the password of the currently authenticated user.
     * Returns a 200 OK response if the password is updated successfully.
     * Returns a 400 Bad Request response if the request data is invalid.
     * Returns a 401 Unauthorized response if the user is not authenticated.
     * Returns a 500 Internal Server Error response if an unexpected error occurs.
     * Returns a 503 Service Unavailable response if the service is temporarily unavailable.
     */
    @PostMapping("/update-password")
    @Operation(summary = "Updates the password of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "503", description = "Service unavailable"),
    })
    public ResponseEntity<UserResponse> updatePassword(@Valid @RequestBody UserUpdatePasswordRequest updateRequest) {
        String userId = authInterceptor.getUserId();
        UserResponse response = service.updatePassword(userId, updateRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes the currently authenticated user.
     * Returns a 204 No Content response if the user is deleted successfully.
     * Returns a 401 Unauthorized response if the user is not authenticated.
     * Returns a 500 Internal Server Error response if an unexpected error occurs.
     * Returns a 503 Service Unavailable response if the service is temporarily unavailable.
     */
    @DeleteMapping("/delete")
    @Operation(summary = "Deletes the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "503", description = "Service unavailable"),
    })
    public ResponseEntity<Void> delete() {
        String userId = authInterceptor.getUserId();
        service.remove(userId);
        return ResponseEntity.noContent().build();
    }
}