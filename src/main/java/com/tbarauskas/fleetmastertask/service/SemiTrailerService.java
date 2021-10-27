package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.SemiTrailer;
import com.tbarauskas.fleetmastertask.exception.ResourceNotFoundException;
import com.tbarauskas.fleetmastertask.repository.SemiTrailerRepository;
import org.springframework.stereotype.Service;

@Service
public class SemiTrailerService {

    private final SemiTrailerRepository trailerRepository;

    public SemiTrailerService(SemiTrailerRepository trailerRepository) {
        this.trailerRepository = trailerRepository;
    }

    public SemiTrailer getTrailerById(Long id) {
        return trailerRepository.getSemiTrailerById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
