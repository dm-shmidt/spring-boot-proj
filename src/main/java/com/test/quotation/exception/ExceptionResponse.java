package com.test.quotation.exception;

import java.time.Instant;

public record ExceptionResponse(
        Instant timestamp,
        String error
) {
    public ExceptionResponse(String errorMessage) {
        this(Instant.now(), errorMessage);
    }
}
