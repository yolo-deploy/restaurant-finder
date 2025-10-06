package com.amazingapps.restaurantfinder.exception;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ResponseErrorTest {
    @Test
    void builder_setsAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ResponseError err = ResponseError.builder()
            .timestamp(now)
            .status(404)
            .message("msg")
            .code("CODE")
            .build();
        assertEquals(now, err.getTimestamp());
        assertEquals(404, err.getStatus());
        assertEquals("msg", err.getMessage());
        assertEquals("CODE", err.getCode());
    }
    @Test
    void settersAndGetters_workCorrectly() {
        ResponseError err = new ResponseError();
        LocalDateTime now = LocalDateTime.now();
        err.setTimestamp(now);
        err.setStatus(500);
        err.setMessage("error");
        err.setCode("ERR");
        assertEquals(now, err.getTimestamp());
        assertEquals(500, err.getStatus());
        assertEquals("error", err.getMessage());
        assertEquals("ERR", err.getCode());
    }
}
