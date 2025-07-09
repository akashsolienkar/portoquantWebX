package com.quant.portoquant.domain.simulation.context;

import com.quant.portoquant.domain.analytical.ExpectedReturnModel;
import com.quant.portoquant.domain.analytical.VolatilityModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Context class containing all parameters required for GBM simulation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GBMSimulatorContext implements SimulationContext {
    
    private double initialPrice;
    private int steps;
    private double timeHorizon; // in years (e.g., 1.0 for one year)
    
    private ExpectedReturnModel expectedReturnModel;
    private VolatilityModel volatilityModel;
}
