package com.amazingapps.restaurantfinder.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;
    private String code;
}