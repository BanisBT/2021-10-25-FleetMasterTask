package com.tbarauskas.fleetmastertask.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.fleetmastertask.entity.Truck;
import com.tbarauskas.fleetmastertask.model.ErrorHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TruckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetTruckById() throws Exception {
        MvcResult result = mockMvc.perform(get("/trucks/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        Truck truck = objectMapper.readValue(result.getResponse().getContentAsString(), Truck.class);

        assertEquals(1L, truck.getId());
        assertEquals("CCC 888", truck.getRegistrationNumber());
        assertEquals("Volvo", truck.getManufacturer());
    }

    @Test
    void testGetTruckByIdIfNotExist() throws Exception {
        MvcResult result = mockMvc.perform(get("/trucks/{id}", 666L))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorHandler error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorHandler.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource with id - 666 not found", error.getMessage());
    }

    @Test
    void testGetTrucks() throws Exception {
        MvcResult result = mockMvc.perform(get("/trucks/list"))
                .andExpect(status().isOk())
                .andReturn();

        List<Truck> trucks = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertEquals(6, trucks.size());
    }

    @Test
    void testGetTrucksWithNoTrailer() throws Exception {
        MvcResult result = mockMvc.perform(get("/trucks/list")
                        .param("trailer", "ZERO"))
                .andExpect(status().isOk())
                .andReturn();

        List<Truck> trucks = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertEquals(2, trucks.size());
        for (Truck truck : trucks) {
            assertNull(truck.getSemiTrailer());
        }
    }

    @Test
    void testGetTrucksWithNoDriver() throws Exception {
        MvcResult result = mockMvc.perform(get("/trucks/list")
                        .param("driver", "ZERO"))
                .andExpect(status().isOk())
                .andReturn();

        List<Truck> trucks = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertEquals(1, trucks.size());
        for (Truck truck : trucks) {
            assertTrue(truck.getDrivers().isEmpty());
        }
    }

    @Test
    void testGetTrucksWithAtLeastOneDriverAndTrailer() throws Exception {
        MvcResult result = mockMvc.perform(get("/trucks/list")
                        .param("driver", "ONE")
                        .param("trailer", "ONE"))
                .andExpect(status().isOk())
                .andReturn();

        List<Truck> trucks = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertEquals(4, trucks.size());
        for (Truck truck : trucks) {
            assertTrue(truck.getDrivers().size() > 0);
            assertTrue(truck.isTruckWithTrailer());
        }
    }
}
