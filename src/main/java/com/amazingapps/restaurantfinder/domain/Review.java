package com.amazingapps.restaurantfinder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String authorName;
    private Double rating;
    private String text;
    private LocalDateTime relativeTimeDescription;
}