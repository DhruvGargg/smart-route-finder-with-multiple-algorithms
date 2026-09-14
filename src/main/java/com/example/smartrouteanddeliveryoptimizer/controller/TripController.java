package com.example.smartrouteanddeliveryoptimizer.controller;

import com.example.smartrouteanddeliveryoptimizer.dto.TripResponse;
import com.example.smartrouteanddeliveryoptimizer.enums.TripStatus;
import com.example.smartrouteanddeliveryoptimizer.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("{/tripId}")
    public TripResponse getTrip(
            @PathVariable Long tripId
    ) {
        return tripService.getTrip(tripId);
    }

    @GetMapping
    public List<TripResponse> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/status/{status}")
    public List<TripResponse> getTripsByStatus(
            @PathVariable TripStatus status
    ) {
        return tripService.getTripsByStatus(status);
    }

    @GetMapping("/truck/{truckId}")
    public List<TripResponse> getTripsByTruck(
            @PathVariable Long truckId
    ) {
        return tripService.getTripsByTruck(truckId);
    }

    @PostMapping("/{tripId}/orders/{orderId}")
    public void assignedToTrip(
            @PathVariable Long tripId,
            @PathVariable Long orderId
    ) {
        tripService.assignOrderToTrip(
                tripId,
                orderId
        );
    }
}
