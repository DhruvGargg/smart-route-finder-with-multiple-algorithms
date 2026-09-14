package com.example.smartrouteanddeliveryoptimizer.entity;

import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delivery_orders")
public class DeliveryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "source_city_id",
            nullable = false
    )
    private City sourceCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "destination_city_id",
            nullable = false
    )
    private City destinationCity;

    private Double packageWeight;

    private Double priority;

    private LocalDateTime orderTime;

    private LocalDateTime expectedDeliveryTime;

    private DeliveryStatus status;

    private LocalDateTime cancellationDeadline;

    private LocalDateTime deliveryDeadline;

    private TripStop tripStop;

}
