package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.SemiTrailer;
import com.tbarauskas.fleetmastertask.entity.Truck;
import com.tbarauskas.fleetmastertask.exception.*;
import com.tbarauskas.fleetmastertask.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruckService {

    private final TruckRepository truckRepository;

    private final SemiTrailerService trailerService;

    public TruckService(TruckRepository truckRepository, SemiTrailerService trailerService) {
        this.truckRepository = truckRepository;
        this.trailerService = trailerService;
    }

    public Truck getTruckById(Long id) {
        return truckRepository.getTruckById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Truck getTruckByRegistrationNumber(String number) {
        return truckRepository.getTruckByRegistrationNumber(number)
                .orElseThrow(() -> new NoVehicleByNumberFoundException(number));
    }

    public List<Truck> getTrucks(String driver, String trailer) {
        List<Truck> trucks = truckRepository.findAll();

        if (driver == null && trailer == null) {
            return trucks;
        } else if (driver == null) {
            return trucks.stream()
                    .filter(truck -> !truck.isTruckWithTrailer())
                    .collect(Collectors.toList());
        } else if (trailer == null) {
            return trucks.stream()
                    .filter(truck -> truck.getDriversListSize() == 0)
                    .collect(Collectors.toList());
        } else if (driver.equalsIgnoreCase("ONE") && (trailer.equalsIgnoreCase("ONE"))) {
            return trucks.stream()
                    .filter(truck -> truck.getDriversListSize() > 0)
                    .filter(Truck::isTruckWithTrailer)
                    .collect(Collectors.toList());
        }
        return trucks;
    }

    public Truck createTruck(Truck truck) {
        if (truckRepository.getTruckByRegistrationNumber(truck.getRegistrationNumber()).isEmpty()) {
            return truckRepository.save(truck);
        }
        throw new LicenseNumberAlreadyExistException(truck.getRegistrationNumber());
    }

    public Truck updateTruck(Long id, Truck truck) {
        Truck truckFromDb = getTruckById(id);
        truckFromDb.setManufacturer(truck.getManufacturer());
        truckFromDb.setProductionDate(truck.getProductionDate());

        if (truck.getRegistrationNumber().equals(truckFromDb.getRegistrationNumber())) {
            return truckRepository.save(truckFromDb);
        } else {
            truckFromDb.setRegistrationNumber(truck.getRegistrationNumber());
            return createTruck(truckFromDb);
        }
    }

    public Truck setTrailerToTruck(Long truckId, String trailerNumber) {
        Truck truck = getTruckById(truckId);
        SemiTrailer trailer = trailerService.getTrailerByRegistrationNumber(trailerNumber);

        if (!truck.isTruckWithTrailer()) {
            if (truck.getSemiTrailer().equals(trailer)) {
                throw new TrailerAlreadyAssignedToThisTruckException();
            } else {
                truck.setSemiTrailer(trailer);
                return truckRepository.save(truck);
            }
        }
        throw new TruckHasTrailerAlreadyException();
    }

    public void deleteTruck(Long id) {
        truckRepository.delete(getTruckById(id));
    }
}
