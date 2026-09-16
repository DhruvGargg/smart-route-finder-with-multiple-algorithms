package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.dto.OrderSelectionResponse;

public interface OrderSelectionService {

    OrderSelectionResponse selectOrdersForTrip(Long tripId);
}
