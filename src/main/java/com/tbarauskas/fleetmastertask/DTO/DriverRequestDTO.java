package com.tbarauskas.fleetmastertask.DTO;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class DriverRequestDTO {

    @NotBlank(message = "Name field can not be empty")
    @Max(value = 50, message = "Name can not be longer then 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can be just from letters and space if it's double name")
    private final String name;

    @NotBlank(message = "Surname field can not be empty")
    @Max(value = 100, message = "Surname can not be longer then 100 symbols")
    @Pattern(regexp = "^[a-zA-Z-]+$", message = "Surname can be just from letters and symbol '-' if it's double surname")
    private final String surname;

    @Min(value = 6, message = "Driver license can not be shorter then 6 symbols")
    @Max(value = 35, message = "Driver license can not be longer then 35 symbols")
    private final String driverLicense;
}
