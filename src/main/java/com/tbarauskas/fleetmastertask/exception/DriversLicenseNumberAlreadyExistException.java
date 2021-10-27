package com.tbarauskas.fleetmastertask.exception;

import lombok.Data;

@Data
public class DriversLicenseNumberAlreadyExistException extends RuntimeException{

    private final String driverLicense;
}
