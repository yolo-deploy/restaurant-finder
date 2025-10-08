package com.amazingapps.restaurantfinder.dto.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserLoginResponse DTO.
 */
class UserLoginResponseTest {

    @Test
    void constructor_ShouldCreateUserLoginResponseWithTokenAndUser() {
        String token = "jwt-token-123";
        UserResponse user = new UserResponse("test@example.com");

        UserLoginResponse response = new UserLoginResponse(token, user);

        assertEquals(token, response.token());
        assertEquals(user, response.user());
        assertEquals("test@example.com", response.user().email());
    }

    @Test
    void constructor_ShouldHandleNullToken() {
        UserResponse user = new UserResponse("test@example.com");

        UserLoginResponse response = new UserLoginResponse(null, user);

        assertNull(response.token());
        assertNotNull(response.user());
    }

    @Test
    void constructor_ShouldHandleNullUser() {
        String token = "jwt-token-123";

        UserLoginResponse response = new UserLoginResponse(token, null);

        assertEquals(token, response.token());
        assertNull(response.user());
    }

    @Test
    void constructor_ShouldHandleEmptyToken() {
        UserResponse user = new UserResponse("test@example.com");

        UserLoginResponse response = new UserLoginResponse("", user);

        assertEquals("", response.token());
        assertNotNull(response.user());
    }

    @Test
    void equals_ShouldReturnTrueForSameContent() {
        UserResponse user = new UserResponse("test@example.com");
        UserLoginResponse response1 = new UserLoginResponse("token", user);
        UserLoginResponse response2 = new UserLoginResponse("token", user);

        assertEquals(response1, response2);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentContent() {
        UserResponse user1 = new UserResponse("test1@example.com");
        UserResponse user2 = new UserResponse("test2@example.com");
        UserLoginResponse response1 = new UserLoginResponse("token", user1);
        UserLoginResponse response2 = new UserLoginResponse("token", user2);

        assertNotEquals(response1, response2);
    }

    @Test
    void hashCode_ShouldBeConsistent() {
        UserResponse user = new UserResponse("test@example.com");
        UserLoginResponse response1 = new UserLoginResponse("token", user);
        UserLoginResponse response2 = new UserLoginResponse("token", user);

        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void toString_ShouldContainTokenAndUser() {
        UserResponse user = new UserResponse("test@example.com");
        UserLoginResponse response = new UserLoginResponse("jwt-token-123", user);

        String toString = response.toString();

        assertTrue(toString.contains("jwt-token-123"));
        assertTrue(toString.contains("test@example.com"));
    }
}
