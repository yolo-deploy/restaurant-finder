package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.domain.RestaurantType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTypeRestControllerTest {

    @InjectMocks
    private RestaurantTypeRestController restaurantTypeRestController;

    @BeforeEach
    void setUp() {
        restaurantTypeRestController = new RestaurantTypeRestController();
    }

    @Test
    void testGetAll_ShouldReturnAllRestaurantTypes() {
        ResponseEntity<RestaurantType[]> response = restaurantTypeRestController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(RestaurantType.values().length, response.getBody().length);

        RestaurantType[] expectedTypes = RestaurantType.values();
        RestaurantType[] actualTypes = response.getBody();

        assertArrayEquals(expectedTypes, actualTypes);
    }

    @Test
    void testGetAll_ShouldReturnCorrectRestaurantTypes() {
        ResponseEntity<RestaurantType[]> response = restaurantTypeRestController.getAll();

        RestaurantType[] types = response.getBody();
        assertNotNull(types);
        assertTrue(types.length > 0);

        boolean containsRussian = false;
        boolean containsItalian = false;
        boolean containsAsian = false;

        for (RestaurantType type : types) {
            if (type == RestaurantType.RUSSIAN) containsRussian = true;
            if (type == RestaurantType.ITALIAN) containsItalian = true;
            if (type == RestaurantType.ASIAN) containsAsian = true;
        }

        assertTrue(containsRussian);
        assertTrue(containsItalian);
        assertTrue(containsAsian);
    }
}