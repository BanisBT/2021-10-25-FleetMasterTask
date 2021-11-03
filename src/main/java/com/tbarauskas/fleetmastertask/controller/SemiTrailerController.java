package com.tbarauskas.fleetmastertask.controller;

import com.tbarauskas.fleetmastertask.DTO.trailer.SemiTrailerRequestDTO;
import com.tbarauskas.fleetmastertask.DTO.trailer.SemiTrailerResponseDTO;
import com.tbarauskas.fleetmastertask.entity.SemiTrailer;
import com.tbarauskas.fleetmastertask.service.SemiTrailerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/trailers")
public class SemiTrailerController {

    private final SemiTrailerService trailerService;

    private final ModelMapper modelMapper;

    public SemiTrailerController(SemiTrailerService trailerService, ModelMapper modelMapper) {
        this.trailerService = trailerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SemiTrailerResponseDTO getTrailerById(@PathVariable Long id) {
        return modelMapper.map(trailerService.getTrailerById(id), SemiTrailerResponseDTO.class);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public SemiTrailerResponseDTO createDriver(@Valid @RequestBody SemiTrailerRequestDTO trailerDTO) {
        SemiTrailer trailer = trailerService.createTrailer(modelMapper.map(trailerDTO, SemiTrailer.class));
        log.debug("Trailer - {} has been created", trailer);
        return modelMapper.map(trailer, SemiTrailerResponseDTO.class);
    }
}
