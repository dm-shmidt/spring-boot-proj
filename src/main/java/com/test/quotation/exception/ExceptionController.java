package com.test.quotation.exception;

import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        if (ex.getMessage().contains("PUBLIC.") && ex.getMessage().contains("_FK")) {
            String message = "Violation of " + ex.getMessage().split("PUBLIC\\.")[1].split("_FK")[0] + ": only unique values are allowed.";
            return ResponseEntity.accepted().body(
                    Map.of("error", message,
                            "timestamp", Instant.now())
            );
        }
        return ResponseEntity.accepted().body(
                Map.of("error", "Data integrity violation exception.",
                        "timestamp", Instant.now())

        );
    }
}
