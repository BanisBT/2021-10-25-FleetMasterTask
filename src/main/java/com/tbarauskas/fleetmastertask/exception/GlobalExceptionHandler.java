package com.tbarauskas.fleetmastertask.exception;

import com.tbarauskas.fleetmastertask.model.ErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorHandler> exceptionHandler(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ErrorHandler(HttpStatus.NOT_FOUND.value(),
                String.format("Resource with id - %d not found", e.getId())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHandler> exceptionHandler(Exception e) {
        log.error("Unexpected error - {}", e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
