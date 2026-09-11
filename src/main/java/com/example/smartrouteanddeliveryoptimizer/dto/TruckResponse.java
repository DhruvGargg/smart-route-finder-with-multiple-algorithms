package com.example.smartrouteanddeliveryoptimizer.dto;

import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TruckResponse {

    private Long id;

    private String registrationNumber;

    private Double maximumCapacity;

    private Double fuelEfficiency;

    private Long currentCityId;

    private String currentCityName;

    private TruckStatus status;

    private LocalDateTime createdAt;
}
