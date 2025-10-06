package com.amazingapps.restaurantfinder.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebServerConfigTest {
    @Test
    void canInstantiate() {
        assertDoesNotThrow(WebServerConfig::new);
    }

    @Test
    void customize_shouldAddDeploymentInfoCustomizers() {
        WebServerConfig config = new WebServerConfig();
        UndertowServletWebServerFactory factory = mock(UndertowServletWebServerFactory.class);
        assertDoesNotThrow(() -> config.customize(factory));
        verify(factory).addDeploymentInfoCustomizers(any());
    }
}
