package com.tbarauskas.fleetmastertask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.fleetmastertask.DTO.driver.DriverRequestDTO;
import com.tbarauskas.fleetmastertask.DTO.driver.DriverResponseDTO;
import com.tbarauskas.fleetmastertask.entity.Driver;
import com.tbarauskas.fleetmastertask.model.ErrorHandler;
import com.tbarauskas.fleetmastertask.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DriverRepository driverRepository;

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

    @Test
    void testCreateDriver() throws Exception {
        DriverRequestDTO driverRequest = new DriverRequestDTO("Created", "Surname", "Test123");

        MvcResult result = mockMvc.perform(post("/drivers/create")
                        .content(objectMapper.writeValueAsString(driverRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        DriverResponseDTO driverResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                DriverResponseDTO.class);

        Driver driverFromDb = driverRepository.getDriverById(driverResponse.getId()).orElse(null);

        assert driverFromDb != null;
        assertEquals(driverRequest.getName(), driverFromDb.getName());
        assertEquals(driverRequest.getSurname(), driverFromDb.getSurname());
        assertEquals(driverRequest.getDriverLicense(), driverFromDb.getDriverLicense());

    }

    @Test
    void testCreateDriverIfDriverLicenseExistInDb() throws Exception {
        DriverRequestDTO driverRequest = new DriverRequestDTO("Created", "Surname", "lcN0001");

        MvcResult result = mockMvc.perform(post("/drivers/create")
                        .content(objectMapper.writeValueAsString(driverRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorHandler error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorHandler.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Driver license - lcN0001 already is taken", error.getMessage());
    }

    @Test
    void testCreateDriverNotValidNameBlank() throws Exception {
        DriverRequestDTO driverRequest = new DriverRequestDTO("  ", "Surname", "lcN0001");

        MvcResult result = mockMvc.perform(post("/drivers/create")
                        .content(objectMapper.writeValueAsString(driverRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorHandler error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorHandler.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Name field can not be empty", error.getMessage());
    }

    @Test
    void testCreateDriverNotValidNameNotOnlyLetters() throws Exception {
        DriverRequestDTO driverRequest = new DriverRequestDTO(" 32523 ", "Surname", "lcN0001");

        MvcResult result = mockMvc.perform(post("/drivers/create")
                        .content(objectMapper.writeValueAsString(driverRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorHandler error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorHandler.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Name can be just from letters and space if it's double name", error.getMessage());
    }

    @Test
    void testCreateDriverNotValidSurnameBlank() throws Exception {
        DriverRequestDTO driverRequest = new DriverRequestDTO("Jonas", "  ", "lcN0001");

        MvcResult result = mockMvc.perform(post("/drivers/create")
                        .content(objectMapper.writeValueAsString(driverRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorHandler error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorHandler.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Surname can be just from letters and symbol '-' if it's double surname", error.getMessage());
    }

    @Test
    void testCreateDriverNotValidDriverLicense() throws Exception {
        DriverRequestDTO driverRequest = new DriverRequestDTO("Jonas", "Surname", "123");

        MvcResult result = mockMvc.perform(post("/drivers/create")
                        .content(objectMapper.writeValueAsString(driverRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorHandler error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorHandler.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Driver license symbols must be in range from 6 to 35", error.getMessage());
    }

    @Test
    void testUpdateDriver() throws Exception {
        DriverRequestDTO driverRequest = new DriverRequestDTO("Update", "UpdateSurname", "LCN0001");

        MvcResult result = mockMvc.perform(put("/drivers/{id}/update", 1L)
                        .content(objectMapper.writeValueAsString(driverRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        DriverResponseDTO driverResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                DriverResponseDTO.class);

        Driver driver = driverRepository.getDriverById(1L).orElse(null);

        assert driver != null;
        assertEquals("Update", driver.getName());
        assertEquals("Update", driverResponse.getName());
        assertEquals("UpdateSurname", driver.getSurname());
        assertEquals("UpdateSurname", driverResponse.getSurname());
        assertEquals("LCN0001", driver.getDriverLicense());
        assertEquals("LCN0001", driverResponse.getDriverLicense());
    }

    @Test
    void testUpdateDriverLicenseExist() throws Exception {
        DriverRequestDTO driverRequest = new DriverRequestDTO("NotUpdate", "NotUpdateSurname",
                "LCN0003");

        MvcResult result = mockMvc.perform(put("/drivers/{id}/update", 1L)
                        .content(objectMapper.writeValueAsString(driverRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorHandler error = objectMapper.readValue(result.getResponse().getContentAsString(),
                ErrorHandler.class);
        Driver driver = driverRepository.getDriverById(1L).orElse(null);

        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Driver license - LCN0003 already is taken", error.getMessage());
        assert driver != null;
        assertNotEquals("NotUpdate", driver.getName());
        assertNotEquals("NotUpdateSurname", driver.getSurname());
    }
}
