package com.test.quotation.exception;

public class RecordWithFKAlreadyExistsException extends RuntimeException{
    public RecordWithFKAlreadyExistsException(String message) {
        super(message);
    }
}
