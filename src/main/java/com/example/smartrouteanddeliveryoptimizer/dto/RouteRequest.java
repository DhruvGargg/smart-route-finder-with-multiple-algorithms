package com.example.smartrouteanddeliveryoptimizer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RouteRequest {

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @NotBlank
    private String algorithm;
}
