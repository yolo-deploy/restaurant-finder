package com.amazingapps.restaurantfinder.dto.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserResponse DTO.
 */
class UserResponseTest {

    @Test
    void constructor_ShouldCreateUserResponseWithEmail() {
        String email = "test@example.com";

        UserResponse response = new UserResponse(email);

        assertEquals(email, response.email());
    }

    @Test
    void constructor_ShouldHandleNullEmail() {
        UserResponse response = new UserResponse(null);

        assertNull(response.email());
    }

    @Test
    void constructor_ShouldHandleEmptyEmail() {
        UserResponse response = new UserResponse("");

        assertEquals("", response.email());
    }

    @Test
    void equals_ShouldReturnTrueForSameContent() {
        UserResponse response1 = new UserResponse("test@example.com");
        UserResponse response2 = new UserResponse("test@example.com");

        assertEquals(response1, response2);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentContent() {
        UserResponse response1 = new UserResponse("test1@example.com");
        UserResponse response2 = new UserResponse("test2@example.com");

        assertNotEquals(response1, response2);
    }

    @Test
    void hashCode_ShouldBeConsistent() {
        UserResponse response1 = new UserResponse("test@example.com");
        UserResponse response2 = new UserResponse("test@example.com");

        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void toString_ShouldContainEmail() {
        UserResponse response = new UserResponse("test@example.com");

        String toString = response.toString();

        assertTrue(toString.contains("test@example.com"));
    }
}
