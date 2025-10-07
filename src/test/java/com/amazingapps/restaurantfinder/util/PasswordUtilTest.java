package com.amazingapps.restaurantfinder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void hashAndMatch_shouldWork() {
        String raw = "mySecretPassword";
        String hashed = PasswordUtil.hashPassword(raw);

        assertNotNull(hashed);
        assertNotEquals(raw, hashed);
        assertTrue(PasswordUtil.matches(raw, hashed));
    }

    @Test
    void matches_wrongPassword_returnsFalse() {
        String raw = "password1";
        String hashed = PasswordUtil.hashPassword("password2");

        assertFalse(PasswordUtil.matches(raw, hashed));
    }
}
