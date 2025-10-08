package com.amazingapps.restaurantfinder.service;

import com.amazingapps.restaurantfinder.domain.User;
import com.amazingapps.restaurantfinder.dto.user.UserLoginRequest;
import com.amazingapps.restaurantfinder.dto.user.UserLoginResponse;
import com.amazingapps.restaurantfinder.dto.user.UserResponse;
import com.amazingapps.restaurantfinder.mapper.UserMapper;
import com.amazingapps.restaurantfinder.repository.UserRepository;
import com.amazingapps.restaurantfinder.security.TokenInteract;
import com.amazingapps.restaurantfinder.security.UserDetailsImpl;
import com.amazingapps.restaurantfinder.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private TokenInteract tokenInteract;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserLoginRequest loginRequest;
    private UserResponse userResponse;
    private UserLoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setEmail("test@example.com");
        user.setPasswordHash("hashedPassword");

        loginRequest = new UserLoginRequest("test@example.com", "password");

        userResponse = new UserResponse("test@example.com");

        loginResponse = new UserLoginResponse("token", userResponse);
    }

    @Test
    void getToken_withId_shouldReturnToken() {
        when(repository.getOrThrow("1")).thenReturn(user);
        when(repository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));
        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class)) {
            passwordUtil.when(() -> PasswordUtil.matches("password", "hashedPassword")).thenReturn(true);
            when(mapper.toResponse(user)).thenReturn(userResponse);
            when(tokenInteract.generateToken(any(UserDetailsImpl.class))).thenReturn("token");
            when(mapper.toLoginResponse(userResponse, "token")).thenReturn(loginResponse);

            UserLoginResponse result = userService.getToken(loginRequest);

            assertEquals(loginResponse, result);
            verify(repository).getOrThrow("1");
            verify(repository, atLeastOnce()).findByEmailIgnoreCase("test@example.com");
            passwordUtil.verify(() -> PasswordUtil.matches("password", "hashedPassword"));
            verify(mapper).toResponse(user);
            verify(tokenInteract).generateToken(any(UserDetailsImpl.class));
            verify(mapper).toLoginResponse(userResponse, "token");
        }
    }

    @Test
    void getToken_withoutId_shouldFindByEmailAndReturnToken() {
        when(repository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));
        when(repository.getOrThrow("1")).thenReturn(user);
        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class)) {
            passwordUtil.when(() -> PasswordUtil.matches("password", "hashedPassword")).thenReturn(true);
            when(mapper.toResponse(user)).thenReturn(userResponse);
            when(tokenInteract.generateToken(any(UserDetailsImpl.class))).thenReturn("token");
            when(mapper.toLoginResponse(userResponse, "token")).thenReturn(loginResponse);

            UserLoginResponse result = userService.getToken(loginRequest);

            assertEquals(loginResponse, result);
            verify(repository).findByEmailIgnoreCase("test@example.com");
            verify(repository).getOrThrow("1");
            passwordUtil.verify(() -> PasswordUtil.matches("password", "hashedPassword"));
            verify(mapper).toResponse(user);
            verify(tokenInteract).generateToken(any(UserDetailsImpl.class));
            verify(mapper).toLoginResponse(userResponse, "token");
        }
    }

    @Test
    void validateToken_shouldReturnValidationResult() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(tokenInteract.getToken(request)).thenReturn("token");
        when(tokenInteract.validateToken("token")).thenReturn(true);

        Boolean result = userService.validateToken(request);

        assertTrue(result);
        verify(tokenInteract).getToken(request);
        verify(tokenInteract).validateToken("token");
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {
        when(repository.getOrThrow("test@example.com")).thenReturn(user);

        UserDetailsImpl result = (UserDetailsImpl) userService.loadUserByUsername("test@example.com");

        assertEquals("1", result.getUsername());
        assertEquals("hashedPassword", result.getPassword());
        verify(repository).getOrThrow("test@example.com");
    }

    @Test
    void loadUserByUsername_userNotFound_shouldThrowUsernameNotFoundException() {
        when(repository.getOrThrow("unknown@example.com")).thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknown@example.com"));
        verify(repository).getOrThrow("unknown@example.com");
    }

    @Test
    void findByEmail_shouldReturnUser() {
        when(repository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmail("test@example.com");

        assertEquals(user, result);
        verify(repository).findByEmailIgnoreCase("test@example.com");
    }

    @Test
    void findByEmail_userNotFound_shouldThrowBadCredentialsException() {
        when(repository.findByEmailIgnoreCase("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> userService.findByEmail("unknown@example.com"));
        verify(repository).findByEmailIgnoreCase("unknown@example.com");
    }

    @Test
    void getToken_invalidPassword_shouldThrowBadCredentialsException() {
        when(repository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));
        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class)) {
            passwordUtil.when(() -> PasswordUtil.matches("wrongpassword", "hashedPassword")).thenReturn(false);

            UserLoginRequest invalidRequest = new UserLoginRequest("test@example.com", "wrongpassword");

            assertThrows(BadCredentialsException.class, () -> userService.getToken(invalidRequest));
            verify(repository).findByEmailIgnoreCase("test@example.com");
            passwordUtil.verify(() -> PasswordUtil.matches("wrongpassword", "hashedPassword"));
        }
    }

    @Test
    void validateToken_invalidToken_shouldReturnFalse() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(tokenInteract.getToken(request)).thenReturn("invalidtoken");
        when(tokenInteract.validateToken("invalidtoken")).thenReturn(false);

        Boolean result = userService.validateToken(request);

        assertFalse(result);
        verify(tokenInteract).getToken(request);
        verify(tokenInteract).validateToken("invalidtoken");
    }
}