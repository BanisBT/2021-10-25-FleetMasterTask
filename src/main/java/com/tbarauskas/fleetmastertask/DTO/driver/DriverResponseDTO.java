package com.tbarauskas.fleetmastertask.DTO.driver;

import com.tbarauskas.fleetmastertask.DTO.TruckResponseDTO;
import lombok.Data;

@Data
public class DriverResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private String driverLicense;

    private TruckResponseDTO truck;
}
