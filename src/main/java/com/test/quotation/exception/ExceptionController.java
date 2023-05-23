package com.test.quotation.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({NotFoundException.class, UpdateFailedException.class, RecordWithFKAlreadyExistsException.class})
    public ResponseEntity<Object> handleNotFoundAndUpdateFailedExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                Map.of("error", ex.getMessage(),
                        "timestamp", Instant.now()),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
