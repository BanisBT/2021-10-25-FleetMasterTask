package com.tbarauskas.fleetmastertask.controller;

import com.tbarauskas.fleetmastertask.DTO.driver.DriverRequestDTO;
import com.tbarauskas.fleetmastertask.DTO.driver.DriverResponseDTO;
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

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public DriverResponseDTO updateDriver(@PathVariable Long id, @RequestBody DriverRequestDTO driverRequest) {
        Driver driver = driverService.updateDriver(id, modelMapper.map(driverRequest, Driver.class));
        log.debug("Driver - {} has been updated", driver);
        return modelMapper.map(driver, DriverResponseDTO.class);
    }
}
