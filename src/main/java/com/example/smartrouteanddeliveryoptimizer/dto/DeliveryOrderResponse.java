package com.example.smartrouteanddeliveryoptimizer.dto;

import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DeliveryOrderResponse {

    private Long id;

    private Long sourceCityId;

    private String sourceCityName;

    private Long destinationCityId;

    private String destinationCityName;

    private Double packageWeight;

    private LocalDateTime orderPlacedAt;

    private LocalDateTime cancellationDeadline;

    private LocalDateTime deliveryDeadline;

    private DeliveryStatus status;
}
