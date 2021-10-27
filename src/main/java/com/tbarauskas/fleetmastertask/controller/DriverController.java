package com.tbarauskas.fleetmastertask.controller;

import com.tbarauskas.fleetmastertask.DTO.DriverRequestDTO;
import com.tbarauskas.fleetmastertask.DTO.DriverResponseDTO;
import com.tbarauskas.fleetmastertask.entity.Driver;
import com.tbarauskas.fleetmastertask.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponseDTO createDriver(@Valid @RequestBody DriverRequestDTO driverDTO) {
        Driver driverCreate = driverService.createDriver(modelMapper.map(driverDTO, Driver.class));
        log.debug("Driver - {} has been created", driverCreate);
        return modelMapper.map(driverCreate, DriverResponseDTO.class);
    }
}
