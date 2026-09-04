package com.example.smartrouteanddeliveryoptimizer.entity;

import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;

    private Double capacity;

    private Double currentLoad;

    private String currentCity;

    private Double fuelEfficiency;

    private TruckStatus status;

    private LocalDateTime createdAt;
}
