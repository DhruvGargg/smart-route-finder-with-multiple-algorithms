package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.dto.CreateTripRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.TripResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.*;
import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import com.example.smartrouteanddeliveryoptimizer.enums.TripStatus;
import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;
import com.example.smartrouteanddeliveryoptimizer.repository.*;
import com.example.smartrouteanddeliveryoptimizer.service.TripService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImplementation implements TripService {

    private final TripRepository tripRepository;
    private final TruckRepository truckRepository;
    private final CityRepository cityRepository;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final TripStopRepository tripStopRepository;

    public TripServiceImplementation(
            TripRepository tripRepository,
            TruckRepository truckRepository,
            CityRepository cityRepository,
            DeliveryOrderRepository deliveryOrderRepository,
            TripStopRepository tripStopRepository
    ) {
        this.tripRepository = tripRepository;
        this.truckRepository = truckRepository;
        this.cityRepository = cityRepository;
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.tripStopRepository = tripStopRepository;
    }

    @Override
    public TripResponse createTrip(CreateTripRequest createTripRequest) {
        Truck truck = truckRepository
                .findById(createTripRequest.getTruckId())
                .orElseThrow(() -> new IllegalArgumentException("Truck not found"));
        if(truck.getStatus() != TruckStatus.AVAILABLE) {
            throw new RuntimeException("Truck is not available");
        }
        City startCity = cityRepository
                .findById(createTripRequest.getStartCityId())
                .orElseThrow(() -> new IllegalArgumentException("Start city not found"));
        City endCity = cityRepository
                .findById(createTripRequest.getEndCityId())
                .orElseThrow(() -> new IllegalArgumentException("End city not found"));
        if(!truck.getCurrentCity().getId().equals(endCity.getId())) {
            throw new RuntimeException("End city is not the same");
        }
        Trip trip = new Trip();
        trip.setTruck(truck);
        trip.setEndCity(endCity);
        trip.setPlannedStartTime(createTripRequest.getPlannedStartTime());
        trip.setStatus(TripStatus.PLANNED);
        Trip savedTrip = tripRepository.save(trip);
        return mapToResponse(savedTrip);
    }

    @Override
    public TripResponse getTrip(Long tripId) {
        Trip trip = tripRepository
                .findById(tripId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Trip not found")
                );
        return mapToResponse(trip);
    }

    @Override
    public List<TripResponse> getAllTrips() {
        return tripRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TripResponse> getTripsByStatus(TripStatus status) {
        return tripRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TripResponse> getTripsByTruck(Long truckId) {
        return tripRepository
                .findByTruckId(truckId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void assignOrderToTrip(Long tripId, Long orderId) {
        Trip trip = tripRepository
                .findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        DeliveryOrder order = deliveryOrderRepository
                .findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(trip.getStatus() != TripStatus.PLANNED) {
            throw new RuntimeException("Order is not planned");
        }

        if(order.getStatus() != DeliveryStatus.PENDING) {
            throw new RuntimeException("Order is not pending");
        }

        Double currentLoad = deliveryOrderRepository
                .getTotalAssignedWeight(tripId);

        Double newLoad = currentLoad + order.getPackageWeight();

        Double truckCapacity =
                trip.getTruck().getMaximumCapacity();

        if(newLoad > truckCapacity) {
            throw new RuntimeException("Capacity exceeded");
        }

        Long destinationCityId =
                order.getDestinationCity().getId();

        TripStop tripStop = tripStopRepository
                .findByTripIdAndCityId(
                        tripId,
                        destinationCityId
                )
                .orElse(null);
        if(tripStop == null) {
            List<TripStop> existingSteps =
                    tripStopRepository
                            .findByTripIdOrderBySequenceNumberAsc(
                                    tripId
                            );
            tripStop = new TripStop();
            tripStop.setTrip(trip);
            tripStop.setCity(order.getDestinationCity());
            tripStop.setSequenceNumber(
                    existingSteps.size() + 1
            );
            tripStop.setDeliveryStop(true);
            tripStop = tripStopRepository.save(tripStop);
        }
        order.setTripStop(tripStop);
        order.setStatus(DeliveryStatus.ASSIGNED);
        deliveryOrderRepository.save(order);
    }

    private TripResponse mapToResponse(Trip trip) {
        return new TripResponse(
                trip.getId(),
                trip.getTruck().getId(),
                trip.getTruck().getRegistrationNumber(),
                trip.getStartCity().getId(),
                trip.getStartCity().getName(),
                trip.getEndCity().getId(),
                trip.getEndCity().getName(),
                trip.getPlannedStartTime(),
                trip.getActualStartTime(),
                trip.getActualEndTime(),
                trip.getStatus()
        );
    }
}
