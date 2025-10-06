package com.amazingapps.restaurantfinder.dto.user;

/**
 * DTO for user login response.
 * Contains JWT token and user information.
 */
public record UserLoginResponse(String token, UserResponse user) {}