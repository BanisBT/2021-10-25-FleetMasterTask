package com.tbarauskas.fleetmastertask.controller;

import com.tbarauskas.fleetmastertask.DTO.DriverResponseDTO;
import com.tbarauskas.fleetmastertask.service.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    private final ModelMapper modelMapper;

    public DriverController(DriverService driverService, ModelMapper modelMapper) {
        this.driverService = driverService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DriverResponseDTO getDriverById(@PathVariable Long id) {
        return modelMapper.map(driverService.getDriverById(id), DriverResponseDTO.class);
    }
}
