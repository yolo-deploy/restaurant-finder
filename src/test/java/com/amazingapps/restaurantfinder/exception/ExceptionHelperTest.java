package com.amazingapps.restaurantfinder.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionHelperTest {

    @Test
    void getRootCauseMessage_returnsRootCauseMessage() {
        RuntimeException root = new RuntimeException("root-cause");
        Exception ex = new Exception("wrapper", root);

        String msg = ExceptionHelper.getRootCauseMessage(ex);
        assertTrue(msg.contains("root-cause"));
    }

    @Test
    void getJsonExceptionMessage_null_returnsDefault() {
        assertEquals(ExceptionHelper.UNDEFINED_FORMAT_ERROR, ExceptionHelper.getJsonExceptionMessage(null));
    }

    @Test
    void getJsonExceptionMessage_simpleException_returnsRootMessage() {
        HttpMessageConversionException ex = new HttpMessageConversionException("simple error");
        String msg = ExceptionHelper.getJsonExceptionMessage(ex);
        assertTrue(msg.contains("simple error"));
    }

    @Test
    void getJsonExceptionMessage_jsonMappingException_returnsFieldInfo() {
        JsonMappingException.Reference ref = new JsonMappingException.Reference(Object.class, "fieldName");
        JsonMappingException mappingEx = JsonMappingException.fromUnexpectedIOE(new java.io.IOException("bad format"));
        mappingEx.prependPath(ref);
        HttpMessageConversionException ex = new HttpMessageNotReadableException("bad format", mappingEx);
        String msg = ExceptionHelper.getJsonExceptionMessage(ex);
        assertTrue(msg.contains("Format error:"));
        assertTrue(msg.contains("fieldName"));
    }
}
