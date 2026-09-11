package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.dto.CreateTruckRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.TruckResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.City;
import com.example.smartrouteanddeliveryoptimizer.entity.Truck;
import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;
import com.example.smartrouteanddeliveryoptimizer.repository.CityRepository;
import com.example.smartrouteanddeliveryoptimizer.repository.TruckRepository;
import com.example.smartrouteanddeliveryoptimizer.service.TruckService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TruckServiceImplementation implements TruckService {

    private final TruckRepository truckRepository;
    private final CityRepository cityRepository;

    public TruckServiceImplementation(
            TruckRepository truckRepository,
            CityRepository cityRepository
    ) {
        this.truckRepository = truckRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public TruckResponse createTruck(CreateTruckRequest request) {
        if(truckRepository
                .findByRegistrationNumber(
                        request
                                .getRegistrationNumber()
                )
                .isPresent()) {
            throw new RuntimeException(
                    "Truck already exists"
            );
        }
        City currentCity = cityRepository
                .findById(
                        request
                                .getCurrentCityId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "City id not found")
                );
        Truck truck = new Truck();
        truck.setRegistrationNumber(request.getRegistrationNumber());
        truck.setMaximumCapacity(request.getMaximumCapacity());
        truck.setFuelEfficiency(request.getFuelEfficiency());
        truck.setStatus(TruckStatus.AVAILABLE);
        truck.setCreatedAt(LocalDateTime.now());
        Truck savedTruck = truckRepository.save(truck);
        return mapToResponse(savedTruck);
    }

    @Override
    public TruckResponse getTruck(Long truckId) {
        return null;
    }

    @Override
    public List<TruckResponse> getAllTrucks() {
        return List.of();
    }

    @Override
    public List<TruckResponse> getTrucksByStatus(TruckStatus status) {
        return List.of();
    }

    private TruckResponse mapToResponse(Truck truck) {
        return new TruckResponse(
                truck.getId(),
                truck.getRegistrationNumber(),
                truck.getMaximumCapacity(),
                truck.getFuelEfficiency(),
                truck.getCurrentCity().getId(),
                truck.getCurrentCity().getName(),
                truck.getStatus(),
                truck.getCreatedAt()
        );
    }
}
