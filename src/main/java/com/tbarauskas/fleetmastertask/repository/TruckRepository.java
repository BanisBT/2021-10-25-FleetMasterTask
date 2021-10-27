package com.tbarauskas.fleetmastertask.repository;

import com.tbarauskas.fleetmastertask.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {

    Optional<Truck> getTruckById(Long id);
}
