package com.tbarauskas.fleetmastertask.DTO;

import lombok.Data;

@Data
public class DriverResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private String driverLicense;

    private TruckResponseDTO truck;
}
