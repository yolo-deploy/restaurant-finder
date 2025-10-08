package com.amazingapps.restaurantfinder.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTypeTest {

    @Test
    void enumValues_ShouldContainAllExpectedTypes() {
        RestaurantType[] types = RestaurantType.values();

        assertEquals(7, types.length);
        assertTrue(containsType(types, RestaurantType.RUSSIAN));
        assertTrue(containsType(types, RestaurantType.GERMAN));
        assertTrue(containsType(types, RestaurantType.ITALIAN));
        assertTrue(containsType(types, RestaurantType.INDIAN));
        assertTrue(containsType(types, RestaurantType.AMERICAN));
        assertTrue(containsType(types, RestaurantType.ASIAN));
        assertTrue(containsType(types, RestaurantType.OTHER));
    }

    @Test
    void valueOf_ShouldReturnCorrectEnum() {
        assertEquals(RestaurantType.ITALIAN, RestaurantType.valueOf("ITALIAN"));
        assertEquals(RestaurantType.ASIAN, RestaurantType.valueOf("ASIAN"));
        assertEquals(RestaurantType.OTHER, RestaurantType.valueOf("OTHER"));
    }

    @Test
    void valueOf_WithInvalidValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
            RestaurantType.valueOf("INVALID_TYPE"));
    }

    @Test
    void name_ShouldReturnCorrectName() {
        assertEquals("RUSSIAN", RestaurantType.RUSSIAN.name());
        assertEquals("GERMAN", RestaurantType.GERMAN.name());
        assertEquals("ITALIAN", RestaurantType.ITALIAN.name());
    }

    private boolean containsType(RestaurantType[] types, RestaurantType target) {
        for (RestaurantType type : types) {
            if (type == target) {
                return true;
            }
        }
        return false;
    }
}