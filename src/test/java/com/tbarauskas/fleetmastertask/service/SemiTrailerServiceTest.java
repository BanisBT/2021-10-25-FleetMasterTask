package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.SemiTrailer;
import com.tbarauskas.fleetmastertask.exception.ResourceNotFoundException;
import com.tbarauskas.fleetmastertask.repository.SemiTrailerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SemiTrailerServiceTest {

    @Mock
    private SemiTrailer trailer;

    @Mock
    private SemiTrailerRepository trailerRepository;

    @InjectMocks
    private SemiTrailerService trailerService;

    @Test
    void testGetTrailerById() {
        when(trailerRepository.getSemiTrailerById(2L)).thenReturn(Optional.of(trailer));

        trailerService.getTrailerById(2L);

        verify(trailerRepository, times(1)).getSemiTrailerById(2L);
    }

    @Test
    void testGetTrailerByIdIfNotExist() {
        when(trailerRepository.getSemiTrailerById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> trailerService.getTrailerById(456L));
    }
}
