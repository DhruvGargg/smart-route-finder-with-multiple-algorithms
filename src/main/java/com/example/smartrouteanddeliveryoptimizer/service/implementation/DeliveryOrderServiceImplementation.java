package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.dto.CreateDeliveryOrderRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.DeliveryOrderResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.City;
import com.example.smartrouteanddeliveryoptimizer.entity.DeliveryOrder;
import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import com.example.smartrouteanddeliveryoptimizer.repository.CityRepository;
import com.example.smartrouteanddeliveryoptimizer.repository.DeliveryOrderRepository;
import com.example.smartrouteanddeliveryoptimizer.service.DeliveryOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryOrderServiceImplementation implements DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final CityRepository cityRepository;

    public  DeliveryOrderServiceImplementation(
            DeliveryOrderRepository deliveryOrderRepository,
            CityRepository cityRepository
    ) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public DeliveryOrderResponse createOrder(CreateDeliveryOrderRequest request) {
        City sourceCity = cityRepository
                .findById(request.getSourceCityId())
                .orElseThrow(() ->
                        new RuntimeException("Source city not found")
                );
        City destinationCity = cityRepository
                .findById(request.getDestinationCityId())
                .orElseThrow(() ->
                        new RuntimeException("Destination city not found")
                );
        if(sourceCity.equals(destinationCity)){
            throw new  RuntimeException("Source city and destination city are the same");
        }
        LocalDateTime now = LocalDateTime.now();
        DeliveryOrder order = new  DeliveryOrder();
        order.setSourceCity(sourceCity);
        order.setDestinationCity(destinationCity);
        order.setPackageWeight(request.getPackageWeight());
        order.setOrderTime(now);
        order.setCancellationDeadline(
                now.plusDays(3)
        );
        order.setDeliveryDeadline(now.plusDays(7));
        order.setStatus(DeliveryStatus.PENDING);
        DeliveryOrder savedOrder =
                deliveryOrderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    @Override
    public DeliveryOrderResponse getOrder(Long orderId) {
        DeliveryOrder order = deliveryOrderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new  RuntimeException("Order not found")
                );
        return mapToResponse(order);
    }

    @Override
    public List<DeliveryOrderResponse> getAllOrders() {
        return deliveryOrderRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<DeliveryOrderResponse> getOrdersByStatus(DeliveryStatus status) {
        return deliveryOrderRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DeliveryOrderResponse cancelOrder(Long orderId) {
        DeliveryOrder order = deliveryOrderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new  RuntimeException("Order not found")
                );
        LocalDateTime now = LocalDateTime.now();
        if(order.getStatus() == DeliveryStatus.DELIVERED) {
            throw new RuntimeException("Delivered orders cannot be cancelled");
        }
        if(order.getStatus() == DeliveryStatus.CANCELLED) {
            throw new RuntimeException("Cancelled orders cannot be cancelled");
        }
        if(now.isAfter(order.getCancellationDeadline())) {
            throw new RuntimeException("Order cannot be cancelled");
        }
        order.setStatus(DeliveryStatus.CANCELLED);
        DeliveryOrder savedOrder =  deliveryOrderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    private DeliveryOrderResponse mapToResponse(DeliveryOrder order) {
        return new DeliveryOrderResponse(
                order.getId(),
                order.getSourceCity().getId(),
                order.getSourceCity().getName(),
                order.getDestinationCity().getId(),
                order.getDestinationCity().getName(),
                order.getPackageWeight(),
                order.getOrderTime(),
                order.getCancellationDeadline(),
                order.getDeliveryDeadline(),
                order.getStatus()
        );
    }
}
