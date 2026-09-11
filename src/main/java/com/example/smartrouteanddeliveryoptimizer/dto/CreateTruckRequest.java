package com.example.smartrouteanddeliveryoptimizer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTruckRequest {

    @NotBlank
    private String registrationNumber;

    @NotNull
    @Min(1)
    private Double maximumCapacity;

    @NotNull
    @Min(0)
    private Double fuelEfficiency;

    @NotNull
    private Long currentCityId;
}
