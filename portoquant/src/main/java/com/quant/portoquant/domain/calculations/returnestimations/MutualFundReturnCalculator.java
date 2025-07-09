// === calculations/returnestimations/StockExpectedReturnCalculator.java ===
package com.quant.portoquant.domain.calculations.returnestimations;

import com.quant.portoquant.domain.analytical.ConstantExpectedReturnModel;
import com.quant.portoquant.domain.analytical.ExpectedReturnModel;
import com.quant.portoquant.domain.model.Asset;
import com.quant.portoquant.domain.model.enums.AssetType;

import org.springframework.stereotype.Component;

@Component
public class MutualFundReturnCalculator implements ExpectedReturnCalculator {
    @Override
    public ExpectedReturnModel calculate(Asset asset) {
        // Dummy logic - could use historical data or analytics
        return new ConstantExpectedReturnModel(0.08); // 8% return
    }
    
    @Override
    public boolean support(Asset asset) {
        return asset.getType() == AssetType.MUTUAL_FUND; // or BOND, etc.
    }
}
