package com.test.quotation.exception;

import com.test.quotation.exception.errorresponses.ExceptionResponse;
import com.test.quotation.exception.errorresponses.Violation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({NotFoundException.class, UpdateFailedException.class, RecordWithFKAlreadyExistsException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundAndUpdateFailedExceptions(Exception ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleDataIntegrityViolationException(Exception ex) {
        if (ex.getMessage().contains("PUBLIC.") && ex.getMessage().contains("_FK")) {
            String message = "Violation of " + ex.getMessage().split("PUBLIC\\.")[1].split("_FK")[0] + ": only unique values are allowed.";
            return new ExceptionResponse(message);
        }
        return new ExceptionResponse("Data integrity violation exception.");
    }

    @ExceptionHandler(java.time.DateTimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleDateTimeException(Exception ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Violation> errors = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage())
            );
        }
        return new ExceptionResponse(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler(AttachmentFailedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleAttachmentFailedException(AttachmentFailedException e) {
        return new ExceptionResponse(e.getMessage());
    }
}
