package com.tbarauskas.fleetmastertask.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SemiTrailerResponseDTO {

    private Long id;

    private String manufacturer;

    private LocalDate productionDate;

    private String registrationNumber;
}
