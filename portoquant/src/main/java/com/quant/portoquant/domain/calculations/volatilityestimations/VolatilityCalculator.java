package com.quant.portoquant.domain.calculations.volatilityestimations;

import com.quant.portoquant.domain.analytical.VolatilityModel;
import com.quant.portoquant.domain.model.Asset;

public interface VolatilityCalculator {
    
	VolatilityModel calculate(Asset asset);
	
	boolean support(Asset asset);
}
