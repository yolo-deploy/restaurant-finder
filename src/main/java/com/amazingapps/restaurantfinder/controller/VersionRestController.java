package com.amazingapps.restaurantfinder.controller;

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

    @GetMapping()
    public String getAppVersion() {
        return appVersion;
    }
}