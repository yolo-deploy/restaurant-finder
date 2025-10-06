package com.amazingapps.restaurantfinder.domain;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void gettersAndSetters_shouldWork() {
        User user = new User();
        user.setEmail("user@mail.com");
        user.setPasswordHash("hash");
        user.setRestaurantIds(Arrays.asList("r1", "r2"));
        assertEquals("user@mail.com", user.getEmail());
        assertEquals("hash", user.getPasswordHash());
        assertEquals(2, user.getRestaurantIds().size());
        assertTrue(user.getRestaurantIds().contains("r1"));
    }

    @Test
    void defaultConstructor_shouldSetFieldsToNull() {
        User user = new User();
        assertNull(user.getEmail());
        assertNull(user.getPasswordHash());
        assertNull(user.getRestaurantIds());
    }
}
