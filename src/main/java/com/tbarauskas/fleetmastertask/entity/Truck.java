package com.tbarauskas.fleetmastertask.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "truck")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "production_date ")
    private LocalDate productionDate;

    @Column(name = "registration_number")
    private String registrationNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "semi_trailer_id")
    private SemiTrailer semiTrailer;

    @OneToMany(mappedBy = "truck")
    private List<Driver> drivers = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

    public boolean isTruckWithTrailer() {
        return semiTrailer != null;
    }

    public int getDriversListSize() {
        return drivers.size();
    }
}
