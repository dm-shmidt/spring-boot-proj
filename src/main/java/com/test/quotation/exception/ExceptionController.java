package com.test.quotation.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({NotFoundException.class, UpdateFailedException.class, RecordWithFKAlreadyExistsException.class})
    public ResponseEntity<Object> handleNotFoundAndUpdateFailedExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        if (ex.getMessage().contains("PUBLIC.") && ex.getMessage().contains("_FK")) {
            String message = "Violation of " + ex.getMessage().split("PUBLIC\\.")[1].split("_FK")[0] + ": only unique values are allowed.";
            return new ResponseEntity<>(new ExceptionResponse(message),
                    new HttpHeaders(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new ExceptionResponse("Data integrity violation exception."),
                new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(java.time.DateTimeException.class)
    public ResponseEntity<Object> handleDateTimeException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()),
                new HttpHeaders(), HttpStatus.CONFLICT);

    }
}
