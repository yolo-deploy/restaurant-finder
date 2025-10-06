package com.amazingapps.restaurantfinder.security;

import com.amazingapps.restaurantfinder.dto.user.UserLoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private static class SimpleServletInputStream extends ServletInputStream {
        private final InputStream delegate;

        SimpleServletInputStream(InputStream delegate) {
            this.delegate = delegate;
        }

        @Override
        public int read() throws IOException {
            return delegate.read();
        }

        @Override
        public boolean isFinished() {
            try {
                return delegate.available() == 0;
            } catch (IOException e) {
                return true;
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            // not needed for test
        }
    }

    @Test
    void attemptAuthentication_parsesRequestAndDelegatesToAuthenticationManager() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();

        AuthenticationManager authManager = mock(AuthenticationManager.class);
        filter.setAuthenticationManager(authManager);

        UserLoginRequest reqObj = new UserLoginRequest("user@example.com", "passwd");
        String json = new ObjectMapper().writeValueAsString(reqObj);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        InputStream is = new ByteArrayInputStream(json.getBytes());
        ServletInputStream sis = new SimpleServletInputStream(is);

        when(request.getInputStream()).thenReturn(sis);

        Authentication expectedAuth = new UsernamePasswordAuthenticationToken(reqObj.email(), reqObj.password());
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(expectedAuth);

        Authentication result = filter.attemptAuthentication(request, response);

        assertNotNull(result);
        assertEquals(reqObj.email(), result.getPrincipal());
    }

    @Test
    void attemptAuthentication_throwsAppRuntimeExceptionOnIOException() {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        try {
            when(req.getInputStream()).thenThrow(new IOException("fail"));
            filter.attemptAuthentication(req, resp);
            fail("Should throw AppRuntimeException");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof IOException);
        }
    }
}
