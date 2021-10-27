package com.tbarauskas.fleetmastertask.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{

    private final Long id;
}
