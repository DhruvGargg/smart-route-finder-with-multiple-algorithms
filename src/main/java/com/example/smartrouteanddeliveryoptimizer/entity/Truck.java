package com.example.smartrouteanddeliveryoptimizer.entity;

import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
@Table(
        name = "trucks",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "registration_number"
                )
        }
)
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "registration_number",
            nullable = false,
            unique = true
    )
    private String registrationNumber;

    @Min(1)
    @Column(nullable = false)
    private Double maximumCapacity;

    private Double currentLoad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "current_city_id",
            nullable = false
    )
    private String currentCity;

    @Min(0)
    @Column(nullable = false)
    private Double fuelEfficiency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TruckStatus status;

    private LocalDateTime createdAt;
}
