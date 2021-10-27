package com.tbarauskas.fleetmastertask.repository;

import com.tbarauskas.fleetmastertask.entity.SemiTrailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemiTrailerRepository extends JpaRepository<SemiTrailer, Long> {

    Optional<SemiTrailer> getSemiTrailerById(Long id);
}
