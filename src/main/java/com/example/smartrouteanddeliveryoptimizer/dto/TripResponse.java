package com.example.smartrouteanddeliveryoptimizer.dto;

import com.example.smartrouteanddeliveryoptimizer.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TripResponse {

    private Long id;

    private Long truckId;
    private String registrationNumber;

    private Long startCityId;
    private String startCityName;

    private Long endCityId;
    private String endCityName;

    private LocalDateTime plannedStartTime;

    private LocalDateTime actualStartTime;

    private LocalDateTime plannedEndTime;

    private TripStatus status;
}
