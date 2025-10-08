package com.amazingapps.restaurantfinder.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Represents a request for creating a review.
 */
public record ReviewCreateRequest(
        @NotBlank(message = "Author name cannot be blank")
        String authorName,

        @NotNull(message = "Rating cannot be null")
        Double rating,

        @NotBlank(message = "Text cannot be blank")
        String text,

        @NotNull(message = "Relative time description cannot be null")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime relativeTimeDescription,

        @NotNull(message = "Created at cannot be null")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime createdAt
) {}