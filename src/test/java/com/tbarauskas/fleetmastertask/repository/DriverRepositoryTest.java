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
}
