package com.tbarauskas.fleetmastertask.repository;

import com.tbarauskas.fleetmastertask.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> getDriverById(Long id);

    Optional<Driver> getDriverByDriverLicense(String driverLicense);
}
