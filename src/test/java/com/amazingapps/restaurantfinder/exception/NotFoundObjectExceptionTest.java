package com.amazingapps.restaurantfinder.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.*;

class NotFoundObjectExceptionTest {
    @Test
    void constructor_setsFields() {
        NotFoundObjectException ex = new NotFoundObjectException("not found");
        assertEquals(ErrorType.NOT_FOUND, ex.getErrorType());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("not found", ex.getMessage());
    }
    @Test
    void fullConstructor_setsAllFields() {
        Throwable t = new RuntimeException("err");
        NotFoundObjectException ex = new NotFoundObjectException(ErrorType.FORBIDDEN, HttpStatus.FORBIDDEN, "msg", t);
        assertEquals(ErrorType.FORBIDDEN, ex.getErrorType());
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals("msg", ex.getMessage());
        assertEquals(t, ex.getCause());
    }
}

