package com.tbarauskas.fleetmastertask.service;

import com.tbarauskas.fleetmastertask.entity.SemiTrailer;
import com.tbarauskas.fleetmastertask.exception.LicenseNumberAlreadyExistException;
import com.tbarauskas.fleetmastertask.exception.NoVehicleByNumberFoundException;
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

    public SemiTrailer getTrailerByRegistrationNumber(String number) {
        return trailerRepository.getSemiTrailerByRegistrationNumberIgnoreCase(number)
                .orElseThrow(() -> new NoVehicleByNumberFoundException(number));
    }

    public SemiTrailer createTrailer(SemiTrailer trailer) {
        if (trailerRepository.getSemiTrailerByRegistrationNumberIgnoreCase(trailer.getRegistrationNumber()).isPresent()) {
            throw new LicenseNumberAlreadyExistException(trailer.getRegistrationNumber());
        }
        return trailerRepository.save(trailer);
    }
}
