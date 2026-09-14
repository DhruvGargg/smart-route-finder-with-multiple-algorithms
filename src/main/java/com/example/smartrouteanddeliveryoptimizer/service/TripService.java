package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.dto.CreateTripRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.TripResponse;
import com.example.smartrouteanddeliveryoptimizer.enums.TripStatus;

import java.util.List;

public interface TripService {

    TripResponse createTrip(CreateTripRequest createTripRequest);

    TripResponse getTrip(Long tripId);

    List<TripResponse> getAllTrips();

    List<TripResponse> getTripsByStatus(TripStatus tripStatus);

    List<TripResponse> getTripsByTruck(Long truckId);

    void assignOrderToTrip(
            Long tripId,
            Long orderId
    );
}
