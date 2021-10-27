package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.Driver;
import com.tbarauskas.fleetmastertask.exception.ResourceNotFoundException;
import com.tbarauskas.fleetmastertask.repository.DriverRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver getDriverById(Long id) {
        return driverRepository.getDriverById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
