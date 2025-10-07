package com.amazingapps.restaurantfinder.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void openingHours_and_review_gettersSetters_work() {
        Restaurant.OpeningHours oh = new Restaurant.OpeningHours();
        oh.setOpenNow(true);
        oh.setWeekdayText(List.of("Mon: 9-17"));
        assertTrue(oh.getOpenNow());
        assertEquals(1, oh.getWeekdayText().size());

        Restaurant.Review r = new Restaurant.Review();
        r.setAuthorName("Author");
        r.setRating(4.5);
        r.setCreatedAt(LocalDateTime.now());
        r.setRelativeTimeDescription(LocalDateTime.now());
        assertEquals("Author", r.getAuthorName());
        assertEquals(4.5, r.getRating());
    }

    @Test
    void fullConstructor_and_setters_work() {
        Restaurant rest = new Restaurant();
        rest.setName("My Restaurant");
        rest.setRating(4.2);
        rest.setPriceLevel(2);
        rest.setPhoneNumber("+123");

        assertEquals("My Restaurant", rest.getName());
        assertEquals(4.2, rest.getRating());
        assertEquals(2, rest.getPriceLevel());
        assertEquals("+123", rest.getPhoneNumber());
    }
}

