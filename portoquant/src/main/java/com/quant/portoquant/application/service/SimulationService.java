package com.quant.portoquant.application.service;

import java.util.List;
import java.util.UUID;
import com.quant.portoquant.api.dto.SimulationResponse;
import com.quant.portoquant.api.dto.SimulationStatusResponse;


public interface SimulationService {
		    
	 	SimulationResponse runSimulation(UUID portofolioId, int numOfSimulations);

	    SimulationResponse getSimulationById(Long simulationId); 
	  
	    List<SimulationResponse> getSimulationsForPortfolio(UUID portfolioId);

		List<SimulationStatusResponse> getRunningSimulations();
		

}
