package com.amazingapps.restaurantfinder.security;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDetailsImplTest {
    @Test
    void builder_setsFields_andUserDetailsMethods() {
        UserDetailsImpl u = UserDetailsImpl.builder()
                .id("user@example.com")
                .passwordHash("pwdhash")
                .build();

        assertEquals("user@example.com", u.getUsername());
        assertEquals("pwdhash", u.getPassword());
        assertNotNull(u.getAuthorities());
    }

    @Test
    void getAuthorities_returnsEmptyList() {
        UserDetailsImpl user = UserDetailsImpl.builder().build();
        assertEquals(Collections.emptyList(), user.getAuthorities());
    }

    @Test
    void getPassword_returnsPasswordHash() {
        UserDetailsImpl user = UserDetailsImpl.builder().passwordHash("hash").build();
        assertEquals("hash", user.getPassword());
    }

    @Test
    void getUsername_returnsId() {
        UserDetailsImpl user = UserDetailsImpl.builder().id("testId").build();
        assertEquals("testId", user.getUsername());
    }

    @Test
    void builder_withNullValues_shouldWork() {
        UserDetailsImpl user = UserDetailsImpl.builder()
                .id(null)
                .passwordHash(null)
                .build();

        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertEquals(Collections.emptyList(), user.getAuthorities());
    }

    @Test
    void builder_withEmptyValues_shouldWork() {
        UserDetailsImpl user = UserDetailsImpl.builder()
                .id("")
                .passwordHash("")
                .build();

        assertEquals("", user.getUsername());
        assertEquals("", user.getPassword());
    }
}
