package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.dto.DeliveryRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.Delivery;
import com.example.smartrouteanddeliveryoptimizer.dto.AssignedDeliveryResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.Vehicle;
import com.example.smartrouteanddeliveryoptimizer.entity.Warehouse;
import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import com.example.smartrouteanddeliveryoptimizer.repository.DeliveryRepository;
import com.example.smartrouteanddeliveryoptimizer.repository.VehicleRepository;
import com.example.smartrouteanddeliveryoptimizer.repository.WarehouseRepository;
import com.example.smartrouteanddeliveryoptimizer.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class DeliveryServiceImplementation implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final RouteServiceImplementation routeService;
    private final WarehouseRepository warehouseRepository;
    private final VehicleRepository vehicleRepository;

    public DeliveryServiceImplementation(
            DeliveryRepository deliveryRepository,
            RouteServiceImplementation routeService,
            WarehouseRepository warehouseRepository,
            VehicleRepository vehicleRepository
    ) {
        this.deliveryRepository = deliveryRepository;
        this.routeService = routeService;
        this.warehouseRepository = warehouseRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Delivery createDelivery(
            DeliveryRequest deliveryRequest
    ) {
        Delivery delivery = new Delivery();
        delivery.setDestination(
                deliveryRequest
                        .getDestination()
        );
        delivery.setType(
                deliveryRequest
                        .getDeliveryType()
        );
        delivery.setDeadline(
                LocalDateTime.now()
                        .plusHours(
                                deliveryRequest
                                        .getDeliveryType()
                                        .getDeliveryHours()
                        )
        );
        delivery.setStatus(
                DeliveryStatus.PENDING
        );
        return deliveryRepository.save(delivery);
    }

    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    @Override
    public PriorityQueue<Delivery> buildDeliveryQueue() {
        List<Delivery> pendingDeliveries =
                deliveryRepository
                        .findByStatus(
                                DeliveryStatus.PENDING
                        );
        PriorityQueue<Delivery> priorityQueue = new PriorityQueue<>(
            Comparator.comparing(
                    Delivery::getDeadline
            )
        );
        priorityQueue.addAll(pendingDeliveries);
        return priorityQueue;
    }

    @Override
    public Delivery getNextDelivery() {
        PriorityQueue<Delivery> priorityQueue =
                buildDeliveryQueue();
        return priorityQueue.poll();
    }

    @Override
    public List<Delivery> getDeliveryProcessingOrder() {
        PriorityQueue<Delivery> priorityQueue = buildDeliveryQueue();
        List<Delivery> deliveryList = new ArrayList<>();
        while(!priorityQueue.isEmpty()) {
            deliveryList.add(priorityQueue.poll());
        }
        return deliveryList;
    }

    @Override
    public AssignedDeliveryResponse assignNextDelivery() {
        PriorityQueue<Delivery> priorityQueue = buildDeliveryQueue();
        Delivery nextDelivery = priorityQueue.poll();
        if(nextDelivery == null) {
            throw new IllegalStateException(
                    "No pending deliveries left on queue"
            );
        }
        RouteResponse routeResponse =
                routeService
                        .findShortestRoute(
                                "Delhi",
                                nextDelivery
                                        .getDestination(),
                                "Dijkstra"
                        );
        nextDelivery.setStatus(
                DeliveryStatus.ASSIGNED
        );
        deliveryRepository.save(nextDelivery);
        return new AssignedDeliveryResponse(
                nextDelivery.getId(),
                nextDelivery.getDestination(),
                nextDelivery.getDeadline(),
                routeResponse.getAlgorithm(),
                routeResponse.getPath(),
                routeResponse.getDistance(),
                routeResponse.getExecutionTime()
        );
    }

    @Override
    public Warehouse findBestWareHouse(String destination) {
        List<Warehouse> warehouses =
                warehouseRepository.findAll();
        Warehouse bestWarehouse = null;
        int minimumDistance = Integer.MAX_VALUE;
        for(Warehouse warehouse : warehouses) {
            RouteResponse route =
                    routeService
                            .findShortestRoute(
                                    warehouse.getCityName(),
                                    destination,
                                    "Dijkstra"
                            );
            if(route.getDistance() != -1 && route.getDistance() < minimumDistance) {
                minimumDistance = route
                        .getDistance();
                bestWarehouse = warehouse;
            }
        }
        return bestWarehouse;
    }

    @Override
    public Vehicle findSuitableVehicle(
            double weight
    ) {
        List<Vehicle>  vehicles =
                vehicleRepository.findByAvailableTrue();
        return vehicles
                .stream()
                .filter(vehicle ->
                        vehicle
                                .getType()
                                .getCapacity() >= weight
                )
                .min(
                        Comparator
                                .comparingInt(
                                        vehicle ->
                                                vehicle
                                                        .getType()
                                                        .getCapacity()
                                )
                )
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Vehicles unavailable for weight"
                                + weight + " kg"
                        )
                );
    }
}
