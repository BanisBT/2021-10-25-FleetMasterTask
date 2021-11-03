package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.Truck;
import com.tbarauskas.fleetmastertask.exception.NoVehicleByNumberFoundException;
import com.tbarauskas.fleetmastertask.exception.ResourceNotFoundException;
import com.tbarauskas.fleetmastertask.repository.TruckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TruckServiceTest {

    @Mock
    private Truck  truck;

    @Mock
    private TruckRepository truckRepository;

    @InjectMocks
    private TruckService truckService;

    @Test
    void testGetTruckById() {
        when(truckRepository.getTruckById(1L)).thenReturn(Optional.of(truck));

        truckService.getTruckById(1L);

        verify(truckRepository, times(1)).getTruckById(1L);
    }

    @Test
    void testGetTruckByIdIfNotExist() {
        when(truckRepository.getTruckById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> truckService.getTruckById(999L));
    }

    @Test
    void testGetTruckByRegistrationNumber() {
        when(truckRepository.getTruckByRegistrationNumber("BBB 888")).thenReturn(Optional.of(truck));

        truckService.getTruckByRegistrationNumber("BBB 888");

        verify(truckRepository, times(1)).getTruckByRegistrationNumber("BBB 888");
    }

    @Test
    void testGetTruckByRegistrationNumberNotExist() {
        when(truckRepository.getTruckByRegistrationNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(NoVehicleByNumberFoundException.class, () -> truckService.getTruckByRegistrationNumber("NotExist67"));
    }
}
