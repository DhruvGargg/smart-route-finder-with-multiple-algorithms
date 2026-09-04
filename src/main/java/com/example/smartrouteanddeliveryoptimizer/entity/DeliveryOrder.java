package com.example.smartrouteanddeliveryoptimizer.entity;

import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class DeliveryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private String destination;

    private Double packageWeight;

    private Double priority;

    private LocalDateTime orderTime;

    private LocalDateTime expectedDeliveryTime;

    private DeliveryStatus status;

}
