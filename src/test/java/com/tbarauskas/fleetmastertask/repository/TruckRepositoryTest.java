package com.tbarauskas.fleetmastertask.repository;

import com.tbarauskas.fleetmastertask.entity.Truck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TruckRepositoryTest {

    @Autowired
    private TruckRepository truckRepository;

    @Test
    void testGetTruckById() {
        Truck truck = truckRepository.getTruckById(1L).orElse(null);

        assert truck != null;
        assertEquals(1L, truck.getId());
        assertEquals("Volvo", truck.getManufacturer());
    }
}
