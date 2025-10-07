package com.amazingapps.restaurantfinder.config;

/**
 * Configuration class for enabling MongoDB auditing.
 * Auditing allows automatic population of creation and modification dates in entities.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {}