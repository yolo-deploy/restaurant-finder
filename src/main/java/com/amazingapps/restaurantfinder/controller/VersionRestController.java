package com.amazingapps.restaurantfinder.controller;

/**
 * REST controller for providing application version information.
 */
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/version")
public class VersionRestController {

    @Value("${app.version}")
    private String appVersion;

    /**
     * Returns the current version of the application.
     * @return application version string
     */
    @GetMapping()
    public String getAppVersion() {
        return appVersion;
    }
}