package com.tbarauskas.fleetmastertask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.fleetmastertask.entity.Driver;
import com.tbarauskas.fleetmastertask.model.ErrorHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDriverById() throws Exception {
        MvcResult result = mockMvc.perform(get("/drivers/{id}", 2L))
                .andExpect(status().isOk())
                .andReturn();

        Driver driver = objectMapper.readValue(result.getResponse().getContentAsString(), Driver.class);

        assertEquals(2L, driver.getId());
        assertEquals("Two", driver.getSurname());
        assertEquals("LCN0002", driver.getDriverLicense());
    }

    @Test
    void testGetDriverByIdIfNotExist() throws Exception {
        MvcResult result = mockMvc.perform(get("/drivers/{id}", 999L))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorHandler error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorHandler.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource with id - 999 not found", error.getMessage());
    }
}
