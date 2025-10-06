package com.amazingapps.restaurantfinder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHours {
    private Boolean openNow;
    private List<String> weekdayText;
}