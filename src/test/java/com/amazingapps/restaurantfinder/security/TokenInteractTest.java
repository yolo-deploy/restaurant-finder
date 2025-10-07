package com.amazingapps.restaurantfinder.security;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TokenInteractTest {

    private TokenInteract tokenInteract;

    @BeforeEach
    void setUp() throws Exception {
        tokenInteract = new TokenInteract();
        // set secretKey (32 bytes) and expirationTime via reflection
        Field secretField = TokenInteract.class.getDeclaredField("secretKey");
        secretField.setAccessible(true);
        secretField.set(tokenInteract, "01234567890123456789012345678901");

        Field expField = TokenInteract.class.getDeclaredField("expirationTime");
        expField.setAccessible(true);
        expField.setLong(tokenInteract, 1000L * 60 * 60); // 1 hour
    }

    @Test
    void generateAndValidateToken_shouldSucceed() {
        org.springframework.security.core.userdetails.UserDetails user = new org.springframework.security.core.userdetails.User("testUser", "pwd", java.util.Collections.emptyList());

        String token = tokenInteract.generateToken(user);
        assertNotNull(token);

        assertTrue(tokenInteract.validateToken(token));

        String username = tokenInteract.getUser(token);
        assertEquals("testUser", username);
    }

    @Test
    void getToken_fromRequest_shouldReturnBearerToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer sometoken");

        String token = tokenInteract.getToken(request);
        assertEquals("sometoken", token);
    }

    @Test
    void getToken_missingOrWrongPrefix_shouldThrow() {
        HttpServletRequest requestNoHeader = mock(HttpServletRequest.class);
        when(requestNoHeader.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        assertThrows(HttpClientErrorException.class, () -> tokenInteract.getToken(requestNoHeader));

        HttpServletRequest requestBad = mock(HttpServletRequest.class);
        when(requestBad.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Token abc");

        assertThrows(HttpClientErrorException.class, () -> tokenInteract.getToken(requestBad));
    }

    @Test
    void validateToken_invalidToken_shouldThrowExecutionConflictException() {
        assertThrows(com.amazingapps.restaurantfinder.exception.ExecutionConflictException.class, () -> tokenInteract.validateToken("invalid.token.here"));
    }
}
