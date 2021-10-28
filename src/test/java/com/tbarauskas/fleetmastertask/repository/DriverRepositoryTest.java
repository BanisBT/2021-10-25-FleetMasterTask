package com.tbarauskas.fleetmastertask.repository;

import com.tbarauskas.fleetmastertask.entity.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Test
    void testGetDriverById() {
        Driver driver = driverRepository.getDriverById(2L).orElse(null);

        assert driver != null;
        assertEquals(2L, driver.getId());
        assertEquals("Two", driver.getSurname());
    }

    @Test
    void testGetDriverByDriverLicenseIgnoreCase() {
        Driver driver = driverRepository.getDriverByDriverLicenseIgnoreCase("LCN0004").orElse(null);

        assert driver != null;
        assertEquals(4, driver.getId());
        assertEquals("LCN0004", driver.getDriverLicense());
    }

    @Test
    void testGetDriverByDriverLicenseIgnoreCaseIfCaseNotCapital() {
        Driver driver = driverRepository.getDriverByDriverLicenseIgnoreCase("lcn0004").orElse(null);

        assert driver != null;
        assertEquals(4, driver.getId());
        assertEquals("LCN0004", driver.getDriverLicense());
        assertNotEquals("lcn0004", driver.getDriverLicense());
    }
}
