package com.tbarauskas.fleetmastertask.exception;

import com.tbarauskas.fleetmastertask.model.ErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(NoVehicleByNumberFoundException.class)
    public ResponseEntity<ErrorHandler> exceptionHandler(NoVehicleByNumberFoundException e) {
        return new ResponseEntity<>(new ErrorHandler(HttpStatus.NOT_FOUND.value(),
                String.format("No truck found by given number - %s", e.getTruckNumber())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DriverIsAlreadyAssignedToThisTruckException.class)
    public ResponseEntity<ErrorHandler> exceptionHandler(DriverIsAlreadyAssignedToThisTruckException e) {
        return new ResponseEntity<>(new ErrorHandler(HttpStatus.BAD_REQUEST.value(),
                "Driver already assigned to this truck"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TrucksAllSeatsAreTakenException.class)
    public ResponseEntity<ErrorHandler> exceptionHandler(TrucksAllSeatsAreTakenException e) {
        log.debug("Trucks with registration number {} all seats are taken", e.getTruckNumber());
        return new ResponseEntity<>(new ErrorHandler(HttpStatus.BAD_REQUEST.value(),
                String.format("Trucks with registration number - %s all seats are taken", e.getTruckNumber())),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LicenseNumberAlreadyExistException.class)
    public ResponseEntity<ErrorHandler> exceptionHandler(LicenseNumberAlreadyExistException e) {
        log.debug("License - {} already exist in data base", e.getDriverLicense());
        return new ResponseEntity<>(new ErrorHandler(HttpStatus.BAD_REQUEST.value(),
                String.format("License number - %s already is taken", e.getDriverLicense())),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorHandler> exceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorHandler(HttpStatus.BAD_REQUEST.value(),
                e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(java.util.stream.Collectors.joining(",  "))),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHandler> exceptionHandler(Exception e) {
        log.error("Unexpected error - {}", e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
