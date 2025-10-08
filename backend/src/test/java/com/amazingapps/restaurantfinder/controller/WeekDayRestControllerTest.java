package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.domain.WeekDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WeekDayRestControllerTest {

    @InjectMocks
    private WeekDayRestController weekDayRestController;

    @BeforeEach
    void setUp() {
        weekDayRestController = new WeekDayRestController();
    }

    @Test
    void testGetAll_ShouldReturnAllWeekDays() {
        ResponseEntity<WeekDay[]> response = weekDayRestController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(WeekDay.values().length, response.getBody().length);

        WeekDay[] expectedDays = WeekDay.values();
        WeekDay[] actualDays = response.getBody();

        assertArrayEquals(expectedDays, actualDays);
    }

    @Test
    void testGetAll_ShouldReturnCorrectWeekDays() {
        ResponseEntity<WeekDay[]> response = weekDayRestController.getAll();

        WeekDay[] days = response.getBody();
        assertNotNull(days);
        assertTrue(days.length > 0);
        assertEquals(7, days.length);
    }
}