package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.Truck;
import com.tbarauskas.fleetmastertask.exception.ResourceNotFoundException;
import com.tbarauskas.fleetmastertask.repository.TruckRepository;
import org.springframework.stereotype.Service;

@Service
public class TruckService {

    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public Truck getTruckById(Long id) {
        return truckRepository.getTruckById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
