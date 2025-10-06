package com.amazingapps.restaurantfinder.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRemoveRequest(@Email(message = "Invalid email format")
                                @NotBlank(message = "Email cannot be blank")
                                String email
) {}