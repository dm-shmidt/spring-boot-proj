package com.test.quotation.exception.errorresponses;

import java.time.Instant;

public record ExceptionResponse(
        Instant timestamp,
        Object error
) {
    public ExceptionResponse(Object errorMessage) {
        this(Instant.now(), errorMessage);
    }
}
