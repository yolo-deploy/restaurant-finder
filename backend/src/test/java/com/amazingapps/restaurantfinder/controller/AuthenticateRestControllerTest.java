package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.dto.user.UserLoginRequest;
import com.amazingapps.restaurantfinder.dto.user.UserLoginResponse;
import com.amazingapps.restaurantfinder.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticateRestControllerTest {

    @Test
    void authenticate_shouldReturnLoginResponse() {
        UserService service = mock(UserService.class);
        AuthenticateRestController controller = new AuthenticateRestController(service);

        UserLoginRequest request = new UserLoginRequest("test@example.com", "pass");
        UserLoginResponse response = new UserLoginResponse("token", new com.amazingapps.restaurantfinder.dto.user.UserResponse("test@example.com"));

        when(service.getToken(request)).thenReturn(response);

        var result = controller.authenticate(request);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(response, result.getBody());
    }

    @Test
    void validate_shouldReturnBoolean() {
        UserService service = mock(UserService.class);
        AuthenticateRestController controller = new AuthenticateRestController(service);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(service.validateToken(request)).thenReturn(Boolean.TRUE);

        var response = controller.validate(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody());
    }
}
