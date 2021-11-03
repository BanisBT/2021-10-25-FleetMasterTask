package com.tbarauskas.fleetmastertask.DTO.truck;

import com.tbarauskas.fleetmastertask.DTO.trailer.SemiTrailerResponseDTO;
import com.tbarauskas.fleetmastertask.DTO.driver.DriverForTruckResponseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TruckResponseDTO {

    private Long id;

    private String manufacturer;

    private LocalDate productionDate;

    private String registrationNumber;

    private SemiTrailerResponseDTO semiTrailer;

    private List<DriverForTruckResponseDTO> drivers;
}
