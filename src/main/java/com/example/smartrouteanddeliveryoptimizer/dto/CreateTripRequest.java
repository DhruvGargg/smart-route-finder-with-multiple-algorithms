package com.example.smartrouteanddeliveryoptimizer.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateTripRequest {

    @NotNull
    private Long truckId;

    @NotNull
    private Long startCityId;

    @NotNull
    private Long endCityId;

    @NotNull
    @FutureOrPresent
    private LocalDateTime plannedStartTime;
}
