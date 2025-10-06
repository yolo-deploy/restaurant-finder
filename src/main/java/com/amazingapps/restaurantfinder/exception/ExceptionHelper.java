package com.amazingapps.restaurantfinder.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.util.ClassUtil;
import org.springframework.http.converter.HttpMessageConversionException;

import java.util.stream.Collectors;

public class ExceptionHelper {

    public static final String UNDEFINED_FORMAT_ERROR = "Undefined format error";

    private ExceptionHelper() {
    }

    public static String getRootCauseMessage(Throwable ex) {
        Throwable rootCause = ClassUtil.getRootCause(ex);
        return ClassUtil.exceptionMessage(rootCause);
    }

    public static String getJsonExceptionMessage(HttpMessageConversionException ex) {
        if (ex == null) {
            return UNDEFINED_FORMAT_ERROR;
        }

        String message = getRootCauseMessage(ex);
        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException error) {
            String field = error.getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));
            return "Format error: " + message + " link to '" + field + "'";
        }
        return message;
    }
}