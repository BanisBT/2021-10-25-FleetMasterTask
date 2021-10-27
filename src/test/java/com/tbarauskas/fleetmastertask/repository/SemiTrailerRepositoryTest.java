package com.tbarauskas.fleetmastertask.repository;

import com.tbarauskas.fleetmastertask.entity.SemiTrailer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SemiTrailerRepositoryTest {

    @Autowired
    private SemiTrailerRepository trailerRepository;

    @Test
    void testGetSemiTrailerById() {
        SemiTrailer trailer = trailerRepository.getSemiTrailerById(3L).orElse(null);

        assert trailer != null;
        assertEquals(3L, trailer.getId());
        assertEquals("Renault", trailer.getManufacturer());
    }
}
