package com.amazingapps.restaurantfinder.security;

import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark controllers or classes that require authentication.
 * Specifies the HandlerInterceptor to use for authorization.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {

    /**
     * The interceptor class to use for authentication.
     * @return HandlerInterceptor class
     */
    Class<? extends HandlerInterceptor> value();
}