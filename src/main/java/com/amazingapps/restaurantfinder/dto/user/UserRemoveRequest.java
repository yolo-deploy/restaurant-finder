package com.amazingapps.restaurantfinder.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for user removal request.
 * Contains email field with validation constraints.
 */
public record UserRemoveRequest(@Email(message = "Invalid email format")
                                @NotBlank(message = "Email cannot be blank")
                                String email
) {}