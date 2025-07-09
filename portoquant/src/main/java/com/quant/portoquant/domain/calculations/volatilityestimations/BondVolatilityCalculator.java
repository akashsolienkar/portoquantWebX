package com.quant.portoquant.domain.calculations.volatilityestimations;

import com.quant.portoquant.domain.analytical.ConstantVolatilityModel;
import com.quant.portoquant.domain.analytical.VolatilityModel;
import com.quant.portoquant.domain.model.Asset;
import com.quant.portoquant.domain.model.enums.AssetType;

import org.springframework.stereotype.Component;

@Component
public class BondVolatilityCalculator implements VolatilityCalculator {
    @Override
    public VolatilityModel calculate(Asset asset) {
        // Dummy logic - could use GARCH or historical stddev
        return new ConstantVolatilityModel(0.15); // 15% volatility
    }
    
    @Override
    public boolean support(Asset asset) {
        return asset.getType() == AssetType.BOND; // or BOND, etc.
    }
}
