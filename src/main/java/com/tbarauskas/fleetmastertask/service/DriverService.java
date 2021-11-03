package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.Driver;
import com.tbarauskas.fleetmastertask.entity.Truck;
import com.tbarauskas.fleetmastertask.exception.DriverIsAlreadyAssignedToThisTruckException;
import com.tbarauskas.fleetmastertask.exception.LicenseNumberAlreadyExistException;
import com.tbarauskas.fleetmastertask.exception.ResourceNotFoundException;
import com.tbarauskas.fleetmastertask.exception.TrucksAllSeatsAreTakenException;
import com.tbarauskas.fleetmastertask.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    private final TruckService truckService;

    public DriverService(DriverRepository driverRepository, TruckService truckService) {
        this.driverRepository = driverRepository;
        this.truckService = truckService;
    }

    public Driver getDriverById(Long id) {
        return driverRepository.getDriverById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Driver createDriver(Driver driver) {
        if (driverRepository.getDriverByDriverLicenseIgnoreCase(driver.getDriverLicense()).equals(Optional.empty())) {
            return driverRepository.save(driver);
        } else {
            throw new LicenseNumberAlreadyExistException(driver.getDriverLicense());
        }
    }

    public Driver updateDriver(Long id, Driver driver) {
        Driver driverFromDb = getDriverById(id);
        driverFromDb.setName(driver.getName());
        driverFromDb.setSurname(driver.getSurname());

        if (driverFromDb.getDriverLicense().equalsIgnoreCase(driver.getDriverLicense())) {
            return driverRepository.save(driverFromDb);
        } else {
            driverFromDb.setDriverLicense(driver.getDriverLicense());
            return createDriver(driverFromDb);
        }
    }

    public Driver setDriverToTruck(Long driverId, String truckNumber) {
        Truck truck = truckService.getTruckByRegistrationNumber(truckNumber);
        Driver driver = getDriverById(driverId);

        if (truck.getDriversListSize() < 2) {
            if (truck.equals(driver.getTruck())) {
                throw new DriverIsAlreadyAssignedToThisTruckException();
            } else {
                driver.setTruck(truck);
                return driverRepository.save(driver);
            }
        } else {
            throw new TrucksAllSeatsAreTakenException(truckNumber);
        }
    }

    public void deleteDriver(Long id) {
        driverRepository.delete(getDriverById(id));
    }
}
