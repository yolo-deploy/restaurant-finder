package com.amazingapps.restaurantfinder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the main App class.
 */
@SpringBootTest
@ActiveProfiles("test")
class AppTest {

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethod_SetsTimezoneCorrectly() {
        App.main(new String[]{});
        assertEquals("Europe/Berlin", TimeZone.getDefault().getID());
    }
}
