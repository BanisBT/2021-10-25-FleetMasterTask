package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.Driver;
import com.tbarauskas.fleetmastertask.exception.DriversLicenseNumberAlreadyExistException;
import com.tbarauskas.fleetmastertask.exception.ResourceNotFoundException;
import com.tbarauskas.fleetmastertask.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver getDriverById(Long id) {
        return driverRepository.getDriverById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Driver createDriver(Driver driver) {
        if (driverRepository.getDriverByDriverLicenseIgnoreCase(driver.getDriverLicense()).equals(Optional.empty())) {
            return driverRepository.save(driver);
        } else {
            throw new DriversLicenseNumberAlreadyExistException(driver.getDriverLicense());
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

    public void deleteDriver(Long id) {
        driverRepository.delete(getDriverById(id));
    }
}
