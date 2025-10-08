package com.amazingapps.restaurantfinder.dto.restaurant;

import com.amazingapps.restaurantfinder.domain.WeekDay;

import java.util.List;

/**
 * DTO for restaurant opening days information.
 */
public record OpeningDaysResponse(Boolean openNow, List<WeekDay> weekDays) {}
