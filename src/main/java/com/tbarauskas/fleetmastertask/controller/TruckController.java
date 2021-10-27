package com.tbarauskas.fleetmastertask.controller;

import com.tbarauskas.fleetmastertask.DTO.TruckResponseDTO;
import com.tbarauskas.fleetmastertask.service.TruckService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trucks")
public class TruckController {

    private final ModelMapper modelMapper;

    private final TruckService truckService;

    public TruckController(ModelMapper modelMapper, TruckService truckService) {
        this.modelMapper = modelMapper;
        this.truckService = truckService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TruckResponseDTO getTruckById(@PathVariable Long id) {
        return modelMapper.map(truckService.getTruckById(id), TruckResponseDTO.class);
    }
}
