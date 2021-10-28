package com.tbarauskas.fleetmastertask.controller;

import com.tbarauskas.fleetmastertask.DTO.driver.DriverRequestDTO;
import com.tbarauskas.fleetmastertask.DTO.driver.DriverResponseDTO;
import com.tbarauskas.fleetmastertask.entity.Driver;
import com.tbarauskas.fleetmastertask.service.DriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/drivers")
@Api(tags = "Driver controller")
public class DriverController {

    private final DriverService driverService;

    private final ModelMapper modelMapper;

    public DriverController(DriverService driverService, ModelMapper modelMapper) {
        this.driverService = driverService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get driver by his ID", tags = "getDriverById", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Driver not found"),
    })
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
    public DriverResponseDTO updateDriver(@PathVariable Long id, @Valid @RequestBody DriverRequestDTO driverRequest) {
        Driver driver = driverService.updateDriver(id, modelMapper.map(driverRequest, Driver.class));
        log.debug("Driver - {} has been updated", driver);
        return modelMapper.map(driver, DriverResponseDTO.class);
    }

    @PatchMapping("{id}/setTruck")
    public DriverResponseDTO setDriverToTruck(@PathVariable Long id,
                                              @RequestParam(name = "truckRegisterNumber") String number) {
        Driver driver = driverService.setDriverToTruck(id, number);
        log.debug(String.format("To truck with registration number - %s was assigned driver with id - %d", number, id));
        return modelMapper.map(driver, DriverResponseDTO.class);
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        log.debug("Driver with id - {} has been deleted", id);
    }
}
