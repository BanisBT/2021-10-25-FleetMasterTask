package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.Driver;
import com.tbarauskas.fleetmastertask.exception.DriversLicenseNumberAlreadyExistException;
import com.tbarauskas.fleetmastertask.exception.ResourceNotFoundException;
import com.tbarauskas.fleetmastertask.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private Driver driver;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @Test
    void testGetDriverById() {
        when(driverRepository.getDriverById(2L)).thenReturn(Optional.of(driver));

        driverService.getDriverById(2L);

        verify(driverRepository, times(1)).getDriverById(2L);
    }

    @Test
    void testGetDriverByIdIfNotExist() {
        when(driverRepository.getDriverById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> driverService.getDriverById(999L));
    }

    @Test
    void testCreateDriver() {
        when(driverRepository.getDriverByDriverLicenseIgnoreCase(driver.getDriverLicense())).thenReturn(Optional.empty());

        driverService.createDriver(driver);

        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void testCreateDriverIfDriverLicenseExist() {
        when(driverRepository.getDriverByDriverLicenseIgnoreCase(driver.getDriverLicense())).thenReturn(Optional.of(driver));

        assertThrows(DriversLicenseNumberAlreadyExistException.class, () -> driverService.createDriver(driver));

        verify(driverRepository, never()).save(any(Driver.class));
    }

    @Test
    void testDeleteDriver() {
        when(driverRepository.getDriverById(driver.getId())).thenReturn(Optional.of(driver));

        driverService.deleteDriver(driver.getId());

        verify(driverRepository, times(1)).getDriverById(driver.getId());
        verify(driverRepository, times(1)).delete(driver);
    }

    @Test
    void testDeleteDriverIfNotExist() {
        when(driverRepository.getDriverById(333L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> driverService.deleteDriver(333L));

        verify(driverRepository, never()).delete(any(Driver.class));
    }
}
