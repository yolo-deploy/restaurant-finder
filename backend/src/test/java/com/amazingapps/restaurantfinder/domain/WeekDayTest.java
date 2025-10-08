package com.amazingapps.restaurantfinder.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeekDayTest {

    @Test
    void enumValues_ShouldContainAllDaysOfWeek() {
        WeekDay[] days = WeekDay.values();

        assertEquals(7, days.length);
        assertEquals(WeekDay.MONDAY, days[0]);
        assertEquals(WeekDay.TUESDAY, days[1]);
        assertEquals(WeekDay.WEDNESDAY, days[2]);
        assertEquals(WeekDay.THURSDAY, days[3]);
        assertEquals(WeekDay.FRIDAY, days[4]);
        assertEquals(WeekDay.SATURDAY, days[5]);
        assertEquals(WeekDay.SUNDAY, days[6]);
    }

    @Test
    void valueOf_ShouldReturnCorrectEnum() {
        assertEquals(WeekDay.MONDAY, WeekDay.valueOf("MONDAY"));
        assertEquals(WeekDay.FRIDAY, WeekDay.valueOf("FRIDAY"));
        assertEquals(WeekDay.SUNDAY, WeekDay.valueOf("SUNDAY"));
    }

    @Test
    void valueOf_WithInvalidValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
            WeekDay.valueOf("INVALID_DAY"));
    }

    @Test
    void name_ShouldReturnCorrectName() {
        assertEquals("MONDAY", WeekDay.MONDAY.name());
        assertEquals("WEDNESDAY", WeekDay.WEDNESDAY.name());
        assertEquals("SUNDAY", WeekDay.SUNDAY.name());
    }

    @Test
    void ordinal_ShouldReturnCorrectOrder() {
        assertEquals(0, WeekDay.MONDAY.ordinal());
        assertEquals(1, WeekDay.TUESDAY.ordinal());
        assertEquals(6, WeekDay.SUNDAY.ordinal());
    }
}