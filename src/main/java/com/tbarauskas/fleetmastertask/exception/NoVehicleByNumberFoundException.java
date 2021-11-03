package com.tbarauskas.fleetmastertask.exception;

import lombok.Data;

@Data
public class NoVehicleByNumberFoundException extends RuntimeException{

    private final String truckNumber;
}
