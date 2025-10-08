package com.amazingapps.restaurantfinder.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        userDetails = UserDetailsImpl.builder()
                .id("test-user-id")
                .passwordHash("hashedPassword123")
                .build();
    }

    @Test
    void constructor_ShouldInitializeCorrectly() {
        assertNotNull(userDetails);
        assertEquals("test-user-id", userDetails.getUsername());
        assertEquals("hashedPassword123", userDetails.getPassword());
    }

    @Test
    void getAuthorities_ShouldReturnEmptyCollection() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    void isAccountNonExpired_ShouldReturnTrue() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked_ShouldReturnTrue() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired_ShouldReturnTrue() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled_ShouldReturnTrue() {
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void getUsername_ShouldReturnUserId() {
        assertEquals("test-user-id", userDetails.getUsername());
    }

    @Test
    void getPassword_ShouldReturnPasswordHash() {
        assertEquals("hashedPassword123", userDetails.getPassword());
    }
}