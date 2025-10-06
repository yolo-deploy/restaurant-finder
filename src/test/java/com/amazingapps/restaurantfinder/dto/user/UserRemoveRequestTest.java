package com.amazingapps.restaurantfinder.dto.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRemoveRequestTest {
    @Test
    void create_validEmail_shouldWork() {
        var req = new UserRemoveRequest("test@example.com");
        assertEquals("test@example.com", req.email());
    }

    @Test
    void create_blankEmail_shouldAllowCreation() {
        var req = new UserRemoveRequest("");
        assertEquals("", req.email());
    }
}
