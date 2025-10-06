package com.amazingapps.restaurantfinder.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class RestExceptionHandlerTest {
    @Test
    void buildResponse_notFound_warnsAndReturnsBody() {
        ResponseEntity<ResponseError> resp = RestExceptionHandler.buildResponse(HttpStatus.NOT_FOUND, ErrorType.NOT_FOUND, "msg", null);
        assertEquals(HttpStatus.NOT_FOUND.value(), resp.getStatusCode().value());
        assertEquals("NOT_FOUND", resp.getBody().getCode());
        assertEquals("msg", resp.getBody().getMessage());
    }
    @Test
    void buildResponse_conflict_warnsAndReturnsBody() {
        ResponseEntity<ResponseError> resp = RestExceptionHandler.buildResponse(HttpStatus.CONFLICT, ErrorType.CONFLICT, "msg", null);
        assertEquals(HttpStatus.CONFLICT.value(), resp.getStatusCode().value());
        assertEquals("CONFLICT", resp.getBody().getCode());
        assertEquals("msg", resp.getBody().getMessage());
    }
    @Test
    void buildResponse_otherStatus_errorsAndReturnsBody() {
        ResponseEntity<ResponseError> resp = RestExceptionHandler.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.UNDEFINED_ERROR, "err", new RuntimeException("fail"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resp.getStatusCode().value());
        assertEquals("UNDEFINED_ERROR", resp.getBody().getCode());
        assertEquals("err", resp.getBody().getMessage());
    }
}

