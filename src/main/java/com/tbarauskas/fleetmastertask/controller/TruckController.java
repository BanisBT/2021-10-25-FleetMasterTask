package com.tbarauskas.fleetmastertask.controller;

import com.tbarauskas.fleetmastertask.DTO.truck.TruckRequestDTO;
import com.tbarauskas.fleetmastertask.DTO.truck.TruckResponseDTO;
import com.tbarauskas.fleetmastertask.entity.Truck;
import com.tbarauskas.fleetmastertask.service.TruckService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<TruckResponseDTO> getTrucks(@RequestParam(required = false, name = "driver") String driver,
                                            @RequestParam(required = false, name = "trailer") String trailer) {
        return truckService.getTrucks(driver, trailer).stream()
                .map(truck -> modelMapper.map(truck, TruckResponseDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public TruckResponseDTO createTruck(@Valid @RequestBody TruckRequestDTO truckRequest) {
        Truck truck = truckService.createTruck(modelMapper.map(truckRequest, Truck.class));
        log.debug("Truck - {} has been created", truck);
        return modelMapper.map(truck, TruckResponseDTO.class);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public TruckResponseDTO updateTruck(@PathVariable Long id, @Valid @RequestBody TruckRequestDTO truckRequest) {
        Truck truck = truckService.updateTruck(id, modelMapper.map(truckRequest, Truck.class));
        log.debug("Truck - {} has been updated", truck);
        return modelMapper.map(truck, TruckResponseDTO.class);
    }

    @PatchMapping("{id}/setTrailer")
    public TruckResponseDTO setTrailerToTruck(@PathVariable Long id,
                                              @RequestParam(name = "trailerRegisterNumber") String number) {
        Truck truck = truckService.setTrailerToTruck(id, number);
        log.debug(String.format("To truck with id - %d was assigned trailer with number - %s", id, number));
        return modelMapper.map(truck, TruckResponseDTO.class);
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTruck(@PathVariable Long id) {
        truckService.deleteTruck(id);
        log.debug("Truck with id - {} has been deleted", id);
    }
}
