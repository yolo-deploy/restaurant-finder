package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.dto.user.UserCreateRequest;
import com.amazingapps.restaurantfinder.dto.user.UserResponse;
import com.amazingapps.restaurantfinder.dto.user.UserUpdatePasswordRequest;
import com.amazingapps.restaurantfinder.security.AuthInterceptor;
import com.amazingapps.restaurantfinder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRestControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private AuthInterceptor authInterceptor;
    @InjectMocks
    private UserRestController userRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void find_success() {
        String userId = "user123";
        UserResponse response = new UserResponse("test@email.com");
        when(authInterceptor.getUserId()).thenReturn(userId);
        when(userService.find(userId)).thenReturn(response);
        ResponseEntity<UserResponse> result = userRestController.find();
        assertEquals(org.springframework.http.HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void create_success() {
        UserCreateRequest req = new UserCreateRequest("test@email.com", "password123");
        doNothing().when(userService).create(req);
        ResponseEntity<Void> result = userRestController.create(req);
        assertEquals(org.springframework.http.HttpStatus.CREATED, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void updatePassword_success() {
        String userId = "user123";
        UserUpdatePasswordRequest req = new UserUpdatePasswordRequest("newPassword123", "oldPassword123");
        UserResponse resp = new UserResponse("test@email.com");
        when(authInterceptor.getUserId()).thenReturn(userId);
        when(userService.updatePassword(userId, req)).thenReturn(resp);
        ResponseEntity<UserResponse> result = userRestController.updatePassword(req);
        assertEquals(org.springframework.http.HttpStatus.OK, result.getStatusCode());
        assertEquals(resp, result.getBody());
    }

    @Test
    void delete_success() {
        String userId = "user123";
        when(authInterceptor.getUserId()).thenReturn(userId);
        doNothing().when(userService).remove(userId);
        ResponseEntity<Void> result = userRestController.delete();
        assertEquals(org.springframework.http.HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }
}
