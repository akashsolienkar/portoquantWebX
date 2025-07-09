package com.quant.portoquant.infrastructure.reports.models;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Summary of simulation data used in portfolio reports.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulationSummary {
   
	 
	
	private double valueAtRisk;
    private double expectedShortfall;
    private double volatilityPercent;
    private double bankruptcyProbability;
    private double meanReturn;
    private double finalPortfolioValue;
    private int numberOfSimulations;
    private String simulationDate;  // optional if you want to show run time
}
