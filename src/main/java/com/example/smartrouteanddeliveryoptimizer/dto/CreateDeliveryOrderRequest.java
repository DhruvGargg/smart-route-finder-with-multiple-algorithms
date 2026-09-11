package com.example.smartrouteanddeliveryoptimizer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDeliveryOrderRequest {

    @NotNull
    private Long sourceCityId;

    @NotNull
    private Long destinationCityId;

    @NotNull
    @Min(0)
    private Double packageWeight;
}
