package com.tbarauskas.fleetmastertask.exception;

import lombok.Data;

@Data
public class TrucksAllSeatsAreTakenException extends RuntimeException {

    private final String truckNumber;
}
