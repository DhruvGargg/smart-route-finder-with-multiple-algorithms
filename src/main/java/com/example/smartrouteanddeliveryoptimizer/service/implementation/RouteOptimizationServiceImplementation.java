package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.dto.OptimizedRouteResponse;
import com.example.smartrouteanddeliveryoptimizer.dto.OptimizedStopResponse;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.Trip;
import com.example.smartrouteanddeliveryoptimizer.entity.TripStop;
import com.example.smartrouteanddeliveryoptimizer.entity.Truck;
import com.example.smartrouteanddeliveryoptimizer.repository.DeliveryOrderRepository;
import com.example.smartrouteanddeliveryoptimizer.repository.TripRepository;
import com.example.smartrouteanddeliveryoptimizer.repository.TripStopRepository;
import com.example.smartrouteanddeliveryoptimizer.service.RouteOptimizationService;
import com.example.smartrouteanddeliveryoptimizer.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteOptimizationServiceImplementation implements RouteOptimizationService {

    private final TripRepository tripRepository;
    private final TripStopRepository tripStopRepository;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final RouteService routeService;

    public RouteOptimizationServiceImplementation(
            TripRepository tripRepository,
            TripStopRepository tripStopRepository,
            DeliveryOrderRepository deliveryOrderRepository,
            RouteService routeService
    ) {
        this.tripRepository = tripRepository;
        this.tripStopRepository = tripStopRepository;
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.routeService = routeService;
    }
    @Override
    public OptimizedRouteResponse optimizeTrip(Long tripId) {
        Trip trip = tripRepository
                .findById(tripId)
                .orElseThrow(() ->
                        new RuntimeException("Trip not found")
                );
        Truck truck = trip.getTruck();
        List<TripStop> tripStops = tripStopRepository
                .findByTripIdOrderBySequenceNumberAsc(tripId);
        if(tripStops.isEmpty()){
            throw new RuntimeException("No trip stops found");
        }
        Map<Long, TripStop> cityStops = new HashMap<>();

        for(TripStop tripStop : tripStops){
            cityStops.putIfAbsent(
                    tripStop.getCity().getId(),
                    tripStop
            );
        }
        List<TripStop> remainingStops =
                new ArrayList<>(cityStops.values());

        List<String> completeRoute = new ArrayList<>();

        List<OptimizedStopResponse> optimizedStops = new ArrayList<>();

        String currentCity =
                trip.getStartCity().getName();

        completeRoute.add(currentCity);

        double totalDistance = 0.0;

        double totalTravelTime = 0.0;

        int sequence = 1;

        while(!remainingStops.isEmpty()){

            TripStop bestStop = null;

            RouteResponse bestRoute = null;

            for(TripStop candidateStop : remainingStops){

                RouteRequest request = new RouteRequest();

                request.setSource(currentCity);
                request.setDestination(
                        candidateStop.getCity().getName()
                );
                request.setAlgorithm("Dijkstra");

                RouteResponse route =
                        routeService.findShortestRoute(request);

                if(bestRoute == null ||
                    route.getDistance() < bestRoute.getDistance()){
                    bestRoute = route;
                    bestStop = candidateStop;
                }
            }
            if(bestStop == null || bestRoute == null){
                throw new RuntimeException("No route found");
            }
            List<String> leg =
                    bestRoute.getPath();

            for(int i = 1; i < leg.size(); i++){
                completeRoute.add(leg.get(i));
            }

            totalDistance += bestRoute.getDistance();
            totalTravelTime += bestRoute.getDistance();

            optimizedStops.add(
                    new OptimizedStopResponse(
                            sequence++,
                            bestStop.getCity().getId(),
                            bestStop.getCity().getName(),
                            totalTravelTime,
                            totalDistance
                    )
            );

            currentCity = bestStop.getCity().getName();

            remainingStops.remove(bestStop);
        }
        RouteRequest routeRequest =
                new RouteRequest();

        routeRequest.setSource(currentCity);
        routeRequest.setDestination(
                trip.getStartCity().getName()
        );
        routeRequest.setAlgorithm("Dijkstra");

        RouteResponse routeResponse =
                routeService.findShortestRoute(routeRequest);

        List<String> responsePath =
                routeResponse.getPath();

        for(int i = 1; i < responsePath.size(); i++){
            completeRoute.add(responsePath.get(i));
        }

        totalDistance += routeResponse.getDistance();
        totalTravelTime += routeResponse.getDistance();

        double fuelConsumed = totalDistance / truck.getFuelEfficiency();

        double fuelPricePerUnit = 100.0;

        double estimatedFuelCost = fuelConsumed * fuelPricePerUnit;

        return new OptimizedRouteResponse(
                trip.getId(),
                truck.getId(),
                trip.getStartCity().getName(),
                trip.getEndCity().getName(),
                completeRoute,
                optimizedStops,
                totalDistance,
                totalTravelTime,
                estimatedFuelCost,
                estimatedFuelCost
        );
    }
}
