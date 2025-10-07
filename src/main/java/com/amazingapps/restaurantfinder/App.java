package com.amazingapps.restaurantfinder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * Main entry point for the Restaurant Finder Spring Boot application.
 * Sets the default timezone and starts the application context.
 */
@Slf4j
@SpringBootApplication
public class App {
    /**
     * Starts the Spring Boot application.
     * @param args command-line arguments
     */
   public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        SpringApplication.run(App.class, args);
    }
}