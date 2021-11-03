package com.tbarauskas.fleetmastertask.exception;

import lombok.Data;

@Data
public class LicenseNumberAlreadyExistException extends RuntimeException{

    private final String driverLicense;
}
