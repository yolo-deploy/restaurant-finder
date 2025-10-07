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

        // UserResponse has single 'email' field
        userResponse = new UserResponse("test@example.com");

        // UserLoginResponse has signature (String token, UserResponse user)
        loginResponse = new UserLoginResponse("token", userResponse);
    }

    @Test
    void getToken_withId_shouldReturnToken() {
        // Arrange
        when(repository.getOrThrow("1")).thenReturn(user);
        when(repository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));
        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class)) {
            passwordUtil.when(() -> PasswordUtil.matches("password", "hashedPassword")).thenReturn(true);
            when(mapper.toResponse(user)).thenReturn(userResponse);
            when(tokenInteract.generateToken(any(UserDetailsImpl.class))).thenReturn("token");
            when(mapper.toLoginResponse(userResponse, "token")).thenReturn(loginResponse);

            // Act
            UserLoginResponse result = userService.getToken("1", loginRequest);

            // Assert
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
        // Arrange
        when(repository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));
        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class)) {
            passwordUtil.when(() -> PasswordUtil.matches("password", "hashedPassword")).thenReturn(true);
            when(mapper.toResponse(user)).thenReturn(userResponse);
            when(tokenInteract.generateToken(any(UserDetailsImpl.class))).thenReturn("token");
            when(mapper.toLoginResponse(userResponse, "token")).thenReturn(loginResponse);

            // Act
            UserLoginResponse result = userService.getToken(null, loginRequest);

            // Assert
            assertEquals(loginResponse, result);
            verify(repository, atLeastOnce()).findByEmailIgnoreCase("test@example.com");
            passwordUtil.verify(() -> PasswordUtil.matches("password", "hashedPassword"));
            verify(mapper).toResponse(user);
            verify(tokenInteract).generateToken(any(UserDetailsImpl.class));
            verify(mapper).toLoginResponse(userResponse, "token");
        }
    }

    @Test
    void validateToken_shouldReturnValidationResult() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(tokenInteract.getToken(request)).thenReturn("token");
        when(tokenInteract.validateToken("token")).thenReturn(true);

        // Act
        Boolean result = userService.validateToken(request);

        // Assert
        assertTrue(result);
        verify(tokenInteract).getToken(request);
        verify(tokenInteract).validateToken("token");
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {
        // Arrange
        when(repository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));

        // Act
        UserDetailsImpl result = (UserDetailsImpl) userService.loadUserByUsername("test@example.com");

        // Assert
        // UserDetailsImpl exposes username and password via UserDetails interface
        assertEquals("test@example.com", result.getUsername());
        assertEquals("hashedPassword", result.getPassword());
        verify(repository).findByEmailIgnoreCase("test@example.com");
    }

    @Test
    void loadUserByUsername_userNotFound_shouldThrowUsernameNotFoundException() {
        // Arrange
        when(repository.findByEmailIgnoreCase("unknown@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknown@example.com"));
        verify(repository).findByEmailIgnoreCase("unknown@example.com");
    }

    @Test
    void findByEmail_shouldReturnUser() {
        // Arrange
        when(repository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));

        // Act
        User result = userService.findByEmail("test@example.com");

        // Assert
        assertEquals(user, result);
        verify(repository).findByEmailIgnoreCase("test@example.com");
    }

    @Test
    void findByEmail_userNotFound_shouldThrowBadCredentialsException() {
        // Arrange
        when(repository.findByEmailIgnoreCase("unknown@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> userService.findByEmail("unknown@example.com"));
        verify(repository).findByEmailIgnoreCase("unknown@example.com");
    }

    @Test
    void getToken_withNullId_usesFindByEmail() {
        user = mock(User.class);
        loginRequest = new UserLoginRequest("mail", "pass");
        when(repository.findByEmailIgnoreCase("mail")).thenReturn(Optional.of(user));
        when(user.getPasswordHash()).thenReturn("hash");
        try (MockedStatic<PasswordUtil> util = mockStatic(PasswordUtil.class)) {
            util.when(() -> PasswordUtil.matches(any(), any())).thenReturn(true);
            when(mapper.toResponse(any())).thenReturn(userResponse);
            when(tokenInteract.generateToken(any())).thenReturn("token");
            when(mapper.toLoginResponse(any(), any())).thenReturn(loginResponse);
            when(user.getEmail()).thenReturn("mail");
            UserLoginResponse resp = userService.getToken(null, loginRequest);
            assertNotNull(resp);
        }
    }

    @Test
    void getToken_withId_usesGetOrThrow() {
        user = mock(User.class);
        loginRequest = new UserLoginRequest("mail", "pass");
        when(repository.getOrThrow(any())).thenReturn(user);
        when(repository.findByEmailIgnoreCase("mail")).thenReturn(Optional.of(user));
        when(user.getPasswordHash()).thenReturn("hash");
        try (MockedStatic<PasswordUtil> util = mockStatic(PasswordUtil.class)) {
            util.when(() -> PasswordUtil.matches(any(), any())).thenReturn(true);
            when(mapper.toResponse(any())).thenReturn(userResponse);
            when(tokenInteract.generateToken(any())).thenReturn("token");
            when(mapper.toLoginResponse(any(), any())).thenReturn(loginResponse);
            when(user.getEmail()).thenReturn("mail");
            UserLoginResponse resp = userService.getToken("id", loginRequest);
            assertNotNull(resp);
        }
    }

    @Test
    void getToken_invalidPassword_throwsBadCredentialsException() {
        user = mock(User.class);
        loginRequest = new UserLoginRequest("mail", "pass");
        when(repository.getOrThrow(any())).thenReturn(user);
        when(user.getPasswordHash()).thenReturn("hash");
        try (MockedStatic<PasswordUtil> util = mockStatic(PasswordUtil.class)) {
            util.when(() -> PasswordUtil.matches(any(), any())).thenReturn(false);
            assertThrows(BadCredentialsException.class, () -> userService.getToken("id", loginRequest));
        }
    }

    @Test
    void loadUserByUsername_notFound_throwsException() {
        when(repository.findByEmailIgnoreCase(any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("mail"));
    }

    @Test
    void validateToken_returnsTrueOrFalse() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(tokenInteract.getToken(req)).thenReturn("token");
        when(tokenInteract.validateToken("token")).thenReturn(true);
        assertTrue(userService.validateToken(req));
        when(tokenInteract.validateToken("token")).thenReturn(false);
        assertFalse(userService.validateToken(req));
    }
}
