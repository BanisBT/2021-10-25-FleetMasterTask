package com.tbarauskas.fleetmastertask.DTO.driver;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DriverForTruckResponseDTO {

    private String name;

    private String surname;

    private String driverLicense;
}
