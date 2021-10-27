package com.tbarauskas.fleetmastertask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.fleetmastertask.entity.SemiTrailer;
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
class SemiTrailerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetTrailerById() throws Exception {
        MvcResult result = mockMvc.perform(get("/trailers/{id}", 3L))
                .andExpect(status().isOk())
                .andReturn();

        SemiTrailer trailer = objectMapper.readValue(result.getResponse().getContentAsString(), SemiTrailer.class);

        assertEquals(3L, trailer.getId());
        assertEquals("Renault", trailer.getManufacturer());
        assertEquals("KLP 009", trailer.getRegistrationNumber());
    }

    @Test
    void testGetTrailerByIdIfNotExist() throws Exception {
        MvcResult result = mockMvc.perform(get("/trailers/{id}", 444L))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorHandler error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorHandler.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource with id - 444 not found", error.getMessage());
    }
}
