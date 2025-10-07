package com.amazingapps.restaurantfinder.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for User domain entity.
 */
class UserTest {

    @Test
    void constructor_ShouldCreateEmptyUser() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getEmail());
        assertNull(user.getPasswordHash());
        assertNull(user.getRestaurantIds());
        assertNull(user.getCreationDate());
        assertNull(user.getModifyDate());
    }

    @Test
    void setEmail_ShouldSetEmailCorrectly() {
        User user = new User();
        String email = "test@example.com";

        user.setEmail(email);

        assertEquals(email, user.getEmail());
    }

    @Test
    void setPasswordHash_ShouldSetPasswordHashCorrectly() {
        User user = new User();
        String passwordHash = "hashedPassword123";

        user.setPasswordHash(passwordHash);

        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    void setRestaurantIds_ShouldSetRestaurantIdsCorrectly() {
        User user = new User();
        List<String> restaurantIds = Arrays.asList("restaurant1", "restaurant2", "restaurant3");

        user.setRestaurantIds(restaurantIds);

        assertEquals(restaurantIds, user.getRestaurantIds());
        assertEquals(3, user.getRestaurantIds().size());
        assertTrue(user.getRestaurantIds().contains("restaurant1"));
        assertTrue(user.getRestaurantIds().contains("restaurant2"));
        assertTrue(user.getRestaurantIds().contains("restaurant3"));
    }

    @Test
    void setId_ShouldSetIdCorrectly() {
        User user = new User();
        String id = "user123";

        user.setId(id);

        assertEquals(id, user.getId());
    }

    @Test
    void setTimestamps_ShouldSetTimestampsCorrectly() {
        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        user.setCreationDate(now);
        user.setModifyDate(now);

        assertEquals(now, user.getCreationDate());
        assertEquals(now, user.getModifyDate());
    }

    @Test
    void fullUserObject_ShouldWorkCorrectly() {
        User user = new User();
        String id = "user123";
        String email = "test@example.com";
        String passwordHash = "hashedPassword123";
        List<String> restaurantIds = Arrays.asList("restaurant1", "restaurant2");
        LocalDateTime now = LocalDateTime.now();

        user.setId(id);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRestaurantIds(restaurantIds);
        user.setCreationDate(now);
        user.setModifyDate(now);

        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(restaurantIds, user.getRestaurantIds());
        assertEquals(now, user.getCreationDate());
        assertEquals(now, user.getModifyDate());
    }

    @Test
    void setEmptyRestaurantIds_ShouldWorkCorrectly() {
        User user = new User();
        List<String> emptyList = List.of();

        user.setRestaurantIds(emptyList);

        assertNotNull(user.getRestaurantIds());
        assertTrue(user.getRestaurantIds().isEmpty());
    }

    @Test
    void setNullValues_ShouldWorkCorrectly() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("hash");

        user.setEmail(null);
        user.setPasswordHash(null);
        user.setRestaurantIds(null);

        assertNull(user.getEmail());
        assertNull(user.getPasswordHash());
        assertNull(user.getRestaurantIds());
    }
}
