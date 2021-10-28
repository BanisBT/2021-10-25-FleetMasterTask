package com.tbarauskas.fleetmastertask.exception;

import lombok.Data;

@Data
public class NoTruckByNumberFoundException extends RuntimeException{

    private final String truckNumber;
}
