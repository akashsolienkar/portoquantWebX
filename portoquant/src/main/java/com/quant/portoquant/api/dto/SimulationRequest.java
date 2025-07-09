package com.quant.portoquant.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SimulationRequest {
    private UUID portfolioId;
    private int numSimulations;
}
