package com.tbarauskas.fleetmastertask.DTO.driver;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class DriverRequestDTO {

    @NotBlank(message = "Name field can not be empty")
    @Size(max = 50, message = "Name can not be longer then 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can be just from letters and space if it's double name")
    @ApiModelProperty(notes = "Driver name", required = true, name = "name", value = "John", example = "John")
    private final String name;

    @Size(max = 100, message = "Surname can not be longer then 100 symbols")
    @Pattern(regexp = "^[a-zA-Z-]+$", message = "Surname can be just from letters and symbol '-' if it's double surname")
    @ApiModelProperty(notes = "Driver surname", required = true, name = "surname", value = "Smith", example = "Smith")
    private final String surname;

    @Size(min = 6, max = 35, message = "Driver license symbols must be in range from 6 to 35")
    @ApiModelProperty(notes = "Driver license number", required = true, name = "Driver license",
            value = "LTU123456", example = "LTU123456")
    private final String driverLicense;
}
