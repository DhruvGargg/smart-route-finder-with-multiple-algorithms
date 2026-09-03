package com.example.smartrouteanddeliveryoptimizer.entity;

import com.example.smartrouteanddeliveryoptimizer.enums.RoadStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roads")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Road {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private Integer distance;

    @Min(0)
    private Integer travelTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoadStatus status =  RoadStatus.ACTIVE;

}
