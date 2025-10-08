package com.amazingapps.restaurantfinder.dto.restaurant;

import com.amazingapps.restaurantfinder.domain.WeekDay;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Represents a request for updating a restaurant's opening hours.
 */
public record RestaurantUpdateOpeningDaysRequest(
        @NotNull(message = "Open now cannot be null")
        Boolean openNow,

        @NotEmpty(message = "Weekdays cannot be empty")
        List<WeekDay> weekdays
) {}