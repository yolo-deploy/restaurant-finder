package com.amazingapps.restaurantfinder.repository;

import com.amazingapps.restaurantfinder.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    @Test
    void findByEmailIgnoreCase_shouldReturnUser() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        User user = new User();
        user.setEmail("test@example.com");
        Mockito.when(repo.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));
        Optional<User> result = repo.findByEmailIgnoreCase("test@example.com");
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
    }

    @Test
    void findByEmailIgnoreCase_shouldReturnEmpty() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        Mockito.when(repo.findByEmailIgnoreCase("notfound@example.com")).thenReturn(Optional.empty());
        Optional<User> result = repo.findByEmailIgnoreCase("notfound@example.com");
        assertFalse(result.isPresent());
    }
}

