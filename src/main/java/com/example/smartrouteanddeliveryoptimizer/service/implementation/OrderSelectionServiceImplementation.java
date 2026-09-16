package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.dto.OrderPriorityResponse;
import com.example.smartrouteanddeliveryoptimizer.dto.OrderSelectionResponse;
import com.example.smartrouteanddeliveryoptimizer.dto.SelectedOrderResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.DeliveryOrder;
import com.example.smartrouteanddeliveryoptimizer.entity.Trip;
import com.example.smartrouteanddeliveryoptimizer.entity.Truck;
import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import com.example.smartrouteanddeliveryoptimizer.exception.ResourceNotFoundException;
import com.example.smartrouteanddeliveryoptimizer.repository.DeliveryOrderRepository;
import com.example.smartrouteanddeliveryoptimizer.repository.TripRepository;
import com.example.smartrouteanddeliveryoptimizer.service.OrderPriorityCalculator;
import com.example.smartrouteanddeliveryoptimizer.service.OrderSelectionService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderSelectionServiceImplementation implements OrderSelectionService {

    private final TripRepository tripRepository;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final OrderPriorityCalculator  orderPriorityCalculator;

    public OrderSelectionServiceImplementation(
            TripRepository tripRepository,
            DeliveryOrderRepository deliveryOrderRepository,
            OrderPriorityCalculator orderPriorityCalculator
    ) {
        this.tripRepository = tripRepository;
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.orderPriorityCalculator = orderPriorityCalculator;
    }

    @Override
    public OrderSelectionResponse selectOrdersForTrip(Long tripId) {
        Trip trip = tripRepository
                .findById(tripId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip Not Found")
                );
        Truck truck = trip.getTruck();

        List<DeliveryOrder> orders = deliveryOrderRepository
                .findByStatusAndSourceCityIdOrderByDeliveryDeadlineAsc(
                        DeliveryStatus.PENDING,
                        trip.getStartCity().getId()
                );
        int capacity = (int) Math.floor(
                truck.getMaximumCapacity()
        );
        int totalOrders = orders.size();
        double[] dp = new double[capacity + 1];

        boolean[][] selected = new boolean[totalOrders][capacity + 1];

        List<OrderPriorityResponse> priorities = new ArrayList<>();

        for(DeliveryOrder order: orders) {
            priorities.add(
                    orderPriorityCalculator.calculateOrderPriority(order)
            );
        }
        for(int i = 0; i < totalOrders; i++) {
            DeliveryOrder deliveryOrder = orders.get(i);
            int weight = (int) Math.ceil(
                    deliveryOrder.getPackageWeight()
            );
            if(weight < 1 || weight > capacity) {
                continue;
            }
            double value = priorities.get(i).getPriorityScore();

            for(int currentCapacity = capacity;
                currentCapacity >= weight;
                currentCapacity--) {
                double newValue =
                        dp[currentCapacity - weight] + value;
                if(newValue > dp[currentCapacity]) {
                    dp[currentCapacity] = newValue;
                    selected[i][currentCapacity] = true;
                }
            }
        }
        List<DeliveryOrder> selectedOrders =
                reconstructSelection(
                        orders,
                        selected,
                        capacity
                );
        double selectedWeight = selectedOrders
                .stream()
                .mapToDouble(DeliveryOrder::getPackageWeight)
                .sum();
        double totalPriorityScore = selectedOrders
                .stream()
                .mapToDouble(order ->
                        orderPriorityCalculator
                                .calculateOrderPriority(order)
                                .getPriorityScore()
                )
                .sum();
        List<SelectedOrderResponse> responseOrders =
                selectedOrders
                        .stream()
                        .map(order -> {
                                    OrderPriorityResponse priority =
                                            orderPriorityCalculator.calculateOrderPriority(order);
                                    return new SelectedOrderResponse(
                                            order.getId(),
                                            order.getDestinationCity().getId(),
                                            order.getDestinationCity().getName(),
                                            order.getPackageWeight(),
                                            priority.getPriority(),
                                            priority.getPriorityScore()
                                    );
                                }
                        )
                        .toList();
        return new OrderSelectionResponse(
                trip.getId(),
                truck.getId(),
                truck.getMaximumCapacity(),
                selectedWeight,
                truck.getMaximumCapacity() - selectedWeight,
                totalPriorityScore,
                responseOrders,
                "0/1 Knapsack - Priority Maximization"
        );
    }
    private List<DeliveryOrder> reconstructSelection(
            List<DeliveryOrder> orders,
            boolean[][] selected,
            int capacity
    ) {
        List<DeliveryOrder> result = new ArrayList<>();

        for(int i = orders.size() - 1; i >= 0; i--) {
            DeliveryOrder deliveryOrder = orders.get(i);
            int weight = (int)  Math.ceil(
                    deliveryOrder.getPackageWeight()
            );

            if(weight <= capacity && selected[i][capacity]) {
                result.add(deliveryOrder);
                capacity -= weight;
            }
        }
        Collections.reverse(result);
        return result;
    }
}
