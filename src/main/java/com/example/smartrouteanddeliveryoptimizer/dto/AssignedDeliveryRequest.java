package com.example.smartrouteanddeliveryoptimizer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignedDeliveryRequest {

    @NotBlank(message = "Source city is required")
    private String source;

    @NotBlank(message = "Algorithm is required")
    private String algorithm;
}
