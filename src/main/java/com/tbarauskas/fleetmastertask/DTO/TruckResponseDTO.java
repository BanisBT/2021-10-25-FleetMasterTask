package com.tbarauskas.fleetmastertask.DTO;

import com.tbarauskas.fleetmastertask.entity.SemiTrailer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TruckResponseDTO {

    private Long id;

    private String manufacturer;

    private LocalDate productionDate;

    private String registrationNumber;

    private SemiTrailer semiTrailer;
}
