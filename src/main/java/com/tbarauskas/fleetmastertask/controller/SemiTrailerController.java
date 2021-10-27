package com.tbarauskas.fleetmastertask.controller;

import com.tbarauskas.fleetmastertask.DTO.SemiTrailerResponseDTO;
import com.tbarauskas.fleetmastertask.service.SemiTrailerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
