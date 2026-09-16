package com.example.smartrouteanddeliveryoptimizer.repository;

import com.example.smartrouteanddeliveryoptimizer.entity.DeliveryOrder;
import com.example.smartrouteanddeliveryoptimizer.entity.Truck;
import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {

    List<DeliveryOrder> findByStatus(DeliveryStatus status);

    List<DeliveryOrder> findByDeliveryCityId(Long deliveryCityId);

    List<DeliveryOrder> findByStatusOrderByDeliveryDeadlineDesc(DeliveryStatus status);

    List<DeliveryOrder> findByStatusOrderByDeliveryDeadlineAsc(DeliveryStatus status);

    @Query(
            """
            SELECT COALESCE(SUM(o.packageWeight), 0)
            FROM DeliveryOrder o
            WHERE o.tripStop.trip.id = :tripId
           """
    )
    Double getTotalAssignedWeight(Long tripId);

    List<DeliveryOrder> findByStatusAndSourceCityIdOrderByDeliveryDeadlineAsc(
            DeliveryStatus status,
            Long sourceCityId
    );
}
