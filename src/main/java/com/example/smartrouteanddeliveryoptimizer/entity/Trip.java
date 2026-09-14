package com.example.smartrouteanddeliveryoptimizer.entity;

import com.example.smartrouteanddeliveryoptimizer.enums.TripStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Truck truck;

    private City startCity;

    private City endCity;

    private LocalDateTime plannedStartTime;

    private TripStatus status;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;
}
