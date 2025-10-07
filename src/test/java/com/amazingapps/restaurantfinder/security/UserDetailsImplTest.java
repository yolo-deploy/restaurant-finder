package com.amazingapps.restaurantfinder.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

class UserDetailsImplTest {
    @Test
    void builder_setsFields_andUserDetailsMethods() {
        UserDetailsImpl u = UserDetailsImpl.builder()
                .id("123")
                .name("user@example.com")
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
    void getUsername_returnsName() {
        UserDetailsImpl user = UserDetailsImpl.builder().name("name").build();
        assertEquals("name", user.getUsername());
    }
}
