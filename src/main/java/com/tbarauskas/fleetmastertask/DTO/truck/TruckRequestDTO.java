package com.tbarauskas.fleetmastertask.DTO.truck;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class TruckRequestDTO {

    @NotBlank(message = "Name field can not be empty")
    private final String manufacturer;

    private final LocalDate productionDate;

    @Size(min = 6, max = 20, message = "Driver license symbols must be in range from 6 to 20")
    private final String registrationNumber;
}
