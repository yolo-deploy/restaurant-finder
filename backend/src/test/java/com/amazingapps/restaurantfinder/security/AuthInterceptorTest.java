package com.amazingapps.restaurantfinder.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthInterceptorTest {
    @Test
    void preHandle_validToken_setsUserAndReturnsTrue() throws Exception {
        TokenInteract tokenInteract = mock(TokenInteract.class);
        AuthInterceptor interceptor = new AuthInterceptor(tokenInteract);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getServletPath()).thenReturn("/api/test");
        when(req.getRequestURI()).thenReturn("/restaurant-finder/api/test");
        when(tokenInteract.getToken(req)).thenReturn("token");
        when(tokenInteract.validateToken("token")).thenReturn(true);
        when(tokenInteract.getUserId("token")).thenReturn("user");

        boolean result = interceptor.preHandle(req, resp, new Object());
        assertTrue(result);
        assertEquals("user", interceptor.getUserId());
    }

    @Test
    void preHandle_invalidToken_returnsFalseAndSendsError() throws Exception {
        TokenInteract tokenInteract = mock(TokenInteract.class);
        AuthInterceptor interceptor = new AuthInterceptor(tokenInteract);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getServletPath()).thenReturn("/api/test");
        when(req.getRequestURI()).thenReturn("/restaurant-finder/api/test");
        when(tokenInteract.getToken(req)).thenReturn("bad");
        when(tokenInteract.validateToken("bad")).thenReturn(false);

        boolean result = interceptor.preHandle(req, resp, new Object());
        assertFalse(result);
        verify(resp).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Test
    void preHandle_blankUser_returnsFalseAndSendsError() throws Exception {
        TokenInteract tokenInteract = mock(TokenInteract.class);
        AuthInterceptor interceptor = new AuthInterceptor(tokenInteract);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getServletPath()).thenReturn("/api/test");
        when(req.getRequestURI()).thenReturn("/restaurant-finder/api/test");
        when(tokenInteract.getToken(req)).thenReturn("token");
        when(tokenInteract.validateToken("token")).thenReturn(true);
        when(tokenInteract.getUserId("token")).thenReturn("");

        boolean result = interceptor.preHandle(req, resp, new Object());
        assertFalse(result);
        verify(resp).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Test
    void postHandle_removesUser() throws Exception {
        TokenInteract tokenInteract = mock(TokenInteract.class);
        AuthInterceptor interceptor = new AuthInterceptor(tokenInteract);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getServletPath()).thenReturn("/api/test");
        when(req.getRequestURI()).thenReturn("/restaurant-finder/api/test");

        when(tokenInteract.getToken(req)).thenReturn("token");
        when(tokenInteract.validateToken("token")).thenReturn(true);
        when(tokenInteract.getUserId("token")).thenReturn("user");
        interceptor.preHandle(req, resp, new Object());

        interceptor.postHandle(req, resp, new Object(), null);
        assertNull(interceptor.getUserId());
    }
}
